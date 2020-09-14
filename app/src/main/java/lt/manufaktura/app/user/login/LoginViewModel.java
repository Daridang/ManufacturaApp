package lt.manufaktura.app.user.login;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-08-09.
 */
public class LoginViewModel extends ViewModel {

    private CompositeDisposable disposable;

    private final LoginRepository repository;

    private MutableLiveData<LoginResponse> _loginResult = new MutableLiveData<>();

    public LiveData<LoginResponse> getLoginResult() {
        return _loginResult;
    }

    @ViewModelInject
    public LoginViewModel(LoginRepository repository) {
        this.repository = repository;
        disposable = new CompositeDisposable();
    }

    public void login(String email, String password) {
        disposable.add(repository.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loginResult -> {
                            if (loginResult.isSuccessful()) {
                                Log.d("TAGGG", "LoginReesult: " + loginResult.toString());
                                _loginResult.postValue(loginResult.body());
                            }
                        },
                        throwable -> {
                            Log.d("TAGGG", "ErrorThrowable " + Objects.requireNonNull(throwable.getMessage()));
                        }
                )
        );
    }

    public String userName() {
        return "Username";
    }
}
