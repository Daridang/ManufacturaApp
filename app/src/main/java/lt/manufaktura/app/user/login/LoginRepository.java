package lt.manufaktura.app.user.login;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private LoginDataApiService apiService;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    @Inject
    public LoginRepository(LoginDataApiService apiService) {
        this.apiService = apiService;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        //dataSource.logout();
    }

    public void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Observable<Response<LoginResponse>> login(String email, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("Username", email);
        params.put("Password", password);
        return apiService.login(params);
    }
}