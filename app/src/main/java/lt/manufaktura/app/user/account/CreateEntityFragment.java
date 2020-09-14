package lt.manufaktura.app.user.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentCreateEntityBinding;


public class CreateEntityFragment extends Fragment {

    public CreateEntityFragment() {
        // Required empty public constructor
    }

    public static CreateEntityFragment newInstance() {
        return new CreateEntityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCreateEntityBinding fceb = DataBindingUtil.inflate(
                inflater, R.layout.fragment_create_entity, container, false
        );

        fceb.createEntityBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_createEntityFragment_to_addPropertiesFragment);
        });

        return fceb.getRoot();
    }
}