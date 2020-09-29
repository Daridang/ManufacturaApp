package lt.manufaktura.app.user.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.jetbrains.annotations.NotNull;

import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_account, container, false
        );

        NavHostFragment navHostFragment = (NavHostFragment)
                getChildFragmentManager().findFragmentById(R.id.account_nav_host_fragment);

        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.accountBottomNavId, controller);
        return binding.getRoot();
    }


}