package su.iota.backend.models;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

public class UserProfile {

    @Nullable
    @Expose
    private Long id;

    @Nullable
    @Expose
    private String login;

    @Nullable
    @Expose
    private String email;

    @Nullable
    private String password;

    @Nullable
    private DateTime birthDate;

    public UserProfile() {
    }

    public UserProfile(long id, @Nullable String login, @Nullable String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    @Nullable
    public String getLogin() {
        return login;
    }

    public void setLogin(@Nullable String login) {
        this.login = login;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    @Nullable
    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@Nullable DateTime birthDate) {
        this.birthDate = birthDate;
    }

}
