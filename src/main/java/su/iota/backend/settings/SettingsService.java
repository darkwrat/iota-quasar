package su.iota.backend.settings;

import co.paralleluniverse.fibers.SuspendExecution;
import org.jetbrains.annotations.NotNull;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface SettingsService {

    int getServerPortSetting() throws SuspendExecution;

    @NotNull String getServerContextPathSetting() throws SuspendExecution;

    @NotNull String getDatabaseUserID();

    @NotNull String getDatabasePassword();

    @NotNull String getDatabaseName();

}
