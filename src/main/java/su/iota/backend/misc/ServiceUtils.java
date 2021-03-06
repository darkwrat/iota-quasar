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
        final ServerHandler<Void, ServiceLocator, Void> serverHandler = new AbstractServerHandler<Void, ServiceLocator, Void>() {
            @Override
            public ServiceLocator handleCall(ActorRef<?> from, Object id, Void m) throws SuspendExecution {
                return serviceLocator;
            }
        };
        //noinspection resource
        final ServerActor<Void, ServiceLocator, Void> actor = new ServerActor<>(ACTOR_NAME, serverHandler);
        actor.spawn();
        actor.register();
    }

    public static ServiceLocator getServiceLocator() throws SuspendExecution, InterruptedException {
        //noinspection unchecked
        return ((Server<Void, ServiceLocator, Void>) ActorRegistry.getActor(ACTOR_NAME)).call(null);
    }

}
