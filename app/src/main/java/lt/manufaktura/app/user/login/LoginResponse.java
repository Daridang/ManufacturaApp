package lt.manufaktura.app.user.login;

import com.google.gson.annotations.Expose;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-08-10.
 */
public class LoginResponse {

    @Expose
    private String token;

    @Expose
    private String expiration;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getExpiration() {
        return this.expiration;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
