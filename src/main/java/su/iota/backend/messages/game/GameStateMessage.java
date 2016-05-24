package su.iota.backend.messages.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;
import su.iota.backend.messages.OutgoingMessage;

public class GameStateMessage implements OutgoingMessage {

    @Nullable
    @SerializedName("__ok")
    @Expose
    private Boolean isOk;

    @Nullable
    @Expose
    private Object payload;

    public GameStateMessage() {
    }

    public GameStateMessage(@Nullable Boolean isOk) {
        this.isOk = isOk;
    }

    @Nullable
    public Boolean getOk() {
        return isOk;
    }

    public void setOk(@Nullable Boolean ok) {
        isOk = ok;
    }

    @Nullable
    public Object getPayload() {
        return payload;
    }

    public void setPayload(@Nullable Object payload) {
        this.payload = payload;
    }

}
