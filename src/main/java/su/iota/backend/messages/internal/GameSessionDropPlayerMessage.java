package su.iota.backend.messages.internal;

import co.paralleluniverse.actors.ActorRef;
import org.jetbrains.annotations.NotNull;
import su.iota.backend.messages.IncomingMessage;
import su.iota.backend.models.UserProfile;

import java.util.Map;

public class GameSessionDropPlayerMessage implements IncomingMessage {

    @NotNull
    private ActorRef<Object> player;

    public GameSessionDropPlayerMessage(@NotNull ActorRef<Object> player) {
        this.player = player;
    }

    @NotNull
    public ActorRef<Object> getPlayer() {
        return player;
    }

}
