package su.iota.backend.frontend.impl;

import co.paralleluniverse.actors.ActorRef;
import co.paralleluniverse.fibers.SuspendExecution;
import com.esotericsoftware.minlog.Log;
import org.apache.commons.beanutils.BeanUtils;
import org.glassfish.hk2.api.PerLookup;
import org.jetbrains.annotations.Nullable;
import org.jvnet.hk2.annotations.Service;
import su.iota.backend.accounts.AccountService;
import su.iota.backend.accounts.exceptions.UserAlreadyExistsException;
import su.iota.backend.accounts.exceptions.UserNotFoundException;
import su.iota.backend.frontend.FrontendService;
import su.iota.backend.game.MatchmakingService;
import su.iota.backend.messages.IncomingMessage;
import su.iota.backend.messages.OutgoingMessage;
import su.iota.backend.models.UserProfile;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;

@Service
@PerLookup
public class FrontendServiceImpl implements FrontendService {

    @Inject
    MatchmakingService matchmakingService;

    @Inject
    AccountService accountService;

    @Override
    public ActorRef<IncomingMessage> getGameSession(UserProfile userProfile, ActorRef<OutgoingMessage> frontend) throws SuspendExecution, InterruptedException {
        return matchmakingService.getGameSession(userProfile, frontend);
    }

    @Override
    public boolean signUp(@Nullable UserProfile userProfile) throws SuspendExecution {
        if (userProfile == null) {
            return false;
        }
        final String login = userProfile.getLogin();
        final String password = userProfile.getPassword();
        if (login == null || password == null) {
            return false;
        }
        try {
            final long userId = accountService.createUser(userProfile);
            userProfile.setId(userId);
            return true;
        } catch (UserAlreadyExistsException ignored) {
        }
        return false;
    }

    @Override
    public boolean checkSignIn(@Nullable UserProfile userProfile) throws SuspendExecution {
        if (userProfile == null) {
            return false;
        }
        final String login = userProfile.getLogin();
        final String password = userProfile.getPassword();
        if (login == null || password == null) {
            return false;
        }
        final Long userId = accountService.getUserId(login);
        if (userId == null) {
            return false;
        }
        try {
            if (accountService.isUserPasswordCorrect(userId, password)) {
                BeanUtils.copyProperties(userProfile, accountService.getUserProfile(userId));
                return true;
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (UserNotFoundException ignored) {
        }
        return false;
    }

    @Override
    public boolean editUser(@Nullable UserProfile userProfile) throws SuspendExecution {
        return false; // todo
    }

    @Override
    public boolean getUserDetails(@Nullable UserProfile userProfile) throws SuspendExecution {
        return false; // todo
    }

    //

}