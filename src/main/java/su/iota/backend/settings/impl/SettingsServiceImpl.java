package su.iota.backend.settings.impl;

import co.paralleluniverse.fibers.SuspendExecution;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.glassfish.hk2.api.Immediate;
import org.jetbrains.annotations.NotNull;
import org.jvnet.hk2.annotations.Service;
import su.iota.backend.settings.SettingsService;

import javax.inject.Singleton;

@Service
@Immediate
public class SettingsServiceImpl implements SettingsService {

    private Config config;

    public SettingsServiceImpl() {
        config = ConfigFactory.load();
        config.checkValid(ConfigFactory.defaultReference());
    }

    @Override
    public int getServerPortSetting() throws SuspendExecution {
        return config.getInt("port");
    }

    @NotNull
    @Override
    public String getServerContextPathSetting() throws SuspendExecution {
        return config.getString("contextPath");
    }

    @NotNull
    @Override
    public String getDatabaseUserID() {
        return config.getString("db.user");
    }

    @NotNull
    @Override
    public String getDatabasePassword() {
        return config.getString("db.password");
    }

    @NotNull
    @Override
    public String getDatabaseName() {
        return config.getString("db.name");
    }
    
}
