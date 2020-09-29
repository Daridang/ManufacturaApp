package lt.manufaktura.app.user.login;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.Utils;
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (prefs.contains("Token")) {
            if (!Utils.isTokenExpired(prefs.getString("Expiration", ""))) {
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_loginFragment_to_accountFragment, null,
                                new NavOptions.Builder()
                                        .setPopUpTo(R.id.loginFragment,
                                                true).build());
            }
        }

        flb = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false
        );

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        flb.setViewmodel(viewModel);

        flb.loginBtnId.setOnClickListener(v -> {
                    String email = flb.emailInput.getText().toString();
                    String password = flb.passwordInput.getText().toString();
                    if (flb.emailInput.getText().toString().isEmpty() || flb.passwordInput.getText().toString().isEmpty()) {
                        flb.emailInput.setError("Please fill all fields");
                    } else {
                        viewModel.login(email, password);
                        flb.loadingBarId.setVisibility(View.VISIBLE);
                        viewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {
                            if (loginResult != null) {
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

                        viewModel.showErrorMessage().observe(getViewLifecycleOwner(), error -> {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
                            flb.loadingBarId.setVisibility(View.GONE);
                        });
                    }
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