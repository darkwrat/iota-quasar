package su.iota.backend.misc;

import co.paralleluniverse.actors.ActorRef;
import co.paralleluniverse.actors.ActorRegistry;
import co.paralleluniverse.actors.behaviors.AbstractServerHandler;
import co.paralleluniverse.actors.behaviors.Server;
import co.paralleluniverse.actors.behaviors.ServerActor;
import co.paralleluniverse.actors.behaviors.ServerHandler;
import co.paralleluniverse.fibers.SuspendExecution;
import org.glassfish.hk2.api.ServiceLocator;

public final class ServiceUtils {

    private static final String ACTOR_NAME = "__aux_ServiceUtilsActor";

    private ServiceUtils() {
    }

    public static void setupServiceUtils(final ServiceLocator serviceLocator) throws SuspendExecution {
        final ServerHandler<Class<?>, Object, Void> serverHandler = new AbstractServerHandler<Class<?>, Object, Void>() {
            @Override
            public Object handleCall(ActorRef<?> from, Object id, Class<?> m) throws SuspendExecution {
                return serviceLocator.getService(m);
            }
        };
        //noinspection resource
        final ServerActor<Class<?>, Object, Void> actor = new ServerActor<>(ACTOR_NAME, serverHandler);
        actor.spawnThread();
        actor.register();
    }

    public static <T> T getService(Class<T> clazz) throws SuspendExecution, InterruptedException {
        //noinspection unchecked
        return (T) ((Server<Class<?>, Object, Void>) ActorRegistry.getActor(ACTOR_NAME)).call(clazz);
    }

}
