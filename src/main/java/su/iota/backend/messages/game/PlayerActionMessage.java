package su.iota.backend.messages.game;

import co.paralleluniverse.actors.ActorRef;
import co.paralleluniverse.actors.behaviors.FromMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;
import su.iota.backend.messages.IncomingMessage;
import su.iota.backend.messages.OutgoingMessage;

public class PlayerActionMessage implements IncomingMessage, FromMessage {

    @Nullable
    @Expose
    private Boolean ready;

    private ActorRef<?> from;

    @Override
    public ActorRef<?> getFrom() {
        return from;
    }

    public void setFrom(ActorRef<?> from) {
        this.from = from;
    }

    @Nullable
    public Boolean getReady() {
        return ready;
    }

    public void setReady(@Nullable Boolean ready) {
        this.ready = ready;
    }

    public static class ResultMessage implements OutgoingMessage {

        @Nullable
        @SerializedName("__ok")
        @Expose
        private Boolean isOk;

        @Expose
        private boolean broadcast = false;

        public ResultMessage() {
        }

        public ResultMessage(@Nullable Boolean isOk) {
            this.isOk = isOk;
        }

        public boolean isBroadcastTrigger() {
            return broadcast;
        }

        public void setBroadcastTrigger(boolean broadcast) {
            this.broadcast = broadcast;
        }

        @Nullable
        public Boolean getOk() {
            return isOk;
        }

        public void setOk(@Nullable Boolean ok) {
            isOk = ok;
        }

    }

}
