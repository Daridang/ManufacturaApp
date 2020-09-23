package lt.manufaktura.app.user.login;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentLoginBinding;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
    private FragmentLoginBinding flb;

    @Inject
    SharedPreferences prefs;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAGG", "wtf: LoginFragment");
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (prefs.contains("Token")) {
            // TODO check token expiration date
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_loginFragment_to_accountFragment, null,
                            new NavOptions.Builder()
                                    .setPopUpTo(R.id.loginFragment,
                                            true).build());
        }

        flb = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false
        );

        flb.setViewmodel(viewModel);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        String email = Objects.requireNonNull(flb.emailInput.getText()).toString();
        String password = Objects.requireNonNull(flb.passwordInput.getText()).toString();

        flb.loginBtnId.setOnClickListener(v -> {
//                    viewModel.login(email, password);
                    flb.loadingBarId.setVisibility(View.VISIBLE);
                    viewModel.login("stalius@manufaktura.lt", "Manufaktura2!");
                    viewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {
                        if (loginResult != null) {
                            Log.d("TAGG", "wtf: " + loginResult.getToken());
                            flb.loadingBarId.setVisibility(View.GONE);
                            SharedPreferences.Editor spEditor = prefs.edit();
                            spEditor.putString("Token", loginResult.getToken());
                            spEditor.putString("Expiration", loginResult.getExpiration());
                            spEditor.apply();

                            NavHostFragment
                                    .findNavController(this)
                                    .navigate(R.id.action_loginFragment_to_accountFragment, null,
                                            new NavOptions.Builder()
                                                    .setPopUpTo(R.id.loginFragment, true)
                                                    .build());
                        }
                    });
                }
        );

//        flb.registerBtnId.setOnClickListener(v -> {
//            NavHostFragment
//                    .findNavController(this)
//                    .navigate(R.id.action_loginFragment_to_registerFragment);
//        });

        return flb.getRoot();
    }

    public void showProgressBar() {
        flb.loadingBarId.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        flb.loadingBarId.setVisibility(View.GONE);
    }
}