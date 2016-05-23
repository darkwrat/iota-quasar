package su.iota.backend.accounts.impl;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import com.esotericsoftware.minlog.Log;
import org.glassfish.hk2.api.Immediate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.SQLDataType;
import org.jvnet.hk2.annotations.Service;
import su.iota.backend.accounts.AccountService;
import su.iota.backend.accounts.exceptions.UserAlreadyExistsException;
import su.iota.backend.accounts.exceptions.UserNotFoundException;
import su.iota.backend.models.UserProfile;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import static org.jooq.impl.DSL.*;

@Service
@Singleton
public class AccountServiceJooqImpl implements AccountService {

    @Inject
    private DSLContext db;

    @Override
    public long createUser(@NotNull UserProfile userProfile) throws SuspendExecution, UserAlreadyExistsException {
        return db.insertInto(table("user"), field("login"), field("email"), field("password"))
                .values(userProfile.getLogin(), userProfile.getEmail(), userProfile.getPassword())
                .returning(field("id"))
                .fetchOne().map(record -> record.getValue("id", Long.class));
    }

    @Override
    public void editUser(long userId, @NotNull UserProfile newUserProfile) throws SuspendExecution, UserNotFoundException, UserAlreadyExistsException {
        throw new UnsupportedOperationException(); // todo
    }

    @Override
    public void deleteUser(long userId) throws SuspendExecution, UserNotFoundException {
        db.deleteFrom(table("user")).where(field("id").eq(userId)).execute();
    }

    @Override
    public @Nullable Long getUserId(@NotNull String userLogin) throws SuspendExecution {
        return (Long) db.selectFrom(table("user")).where(field("login").eq(userLogin)).fetchOne(field("id"));
    }

    @Override
    public @Nullable UserProfile getUserProfile(@NotNull String userLogin) throws SuspendExecution {
        return db.selectFrom(table("user")).where(field("login").eq(userLogin)).fetchOne(new UserProfileRecordMapper());
    }

    @Override
    public @Nullable UserProfile getUserProfile(long userId) {
        return db.selectFrom(table("user")).where(field("id").eq(userId)).fetchOne(new UserProfileRecordMapper());
    }

    @Override
    public boolean isUserPasswordCorrect(long userId, @NotNull String password) throws SuspendExecution, UserNotFoundException {
        return db.fetchExists(selectFrom(table("user")).where(field("id").eq(userId)).and(field("password").eq(password)));
    }

    @Override
    public boolean isUserExistent(long userId) throws SuspendExecution {
        return db.fetchExists(selectFrom(table("user")).where(field("id").eq(userId)));
    }

    @Override
    public boolean isUserExistent(@NotNull String userLogin) throws SuspendExecution {
        return db.fetchExists(selectFrom(table("user")).where(field("login").eq(userLogin)));
    }

    private static class UserProfileRecordMapper implements RecordMapper<Record, UserProfile> {

        @Override
        @Suspendable
        public UserProfile map(Record record) {
            final UserProfile userProfile = new UserProfile();
            userProfile.setId(record.getValue("id", Long.class));
            userProfile.setLogin(record.getValue("login", String.class));
            userProfile.setEmail(record.getValue("email", String.class));
            return userProfile;
        }

    }

}
