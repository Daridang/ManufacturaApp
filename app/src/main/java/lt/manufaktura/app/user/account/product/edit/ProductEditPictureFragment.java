package lt.manufaktura.app.user.account.product.edit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentEditProductPictureBinding;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductEditPictureFragment extends Fragment {

    @Inject
    SharedPreferences prefs;

    private ProductViewModel productViewModel;

    public ProductEditPictureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditProductPictureBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_product_picture, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());

        binding.saveBtnId.setOnClickListener(v -> {
            productViewModel.editProduct("Bearer " + prefs.getString("Token", ""),
                    binding.getProduct());
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productEditPictureFragment_to_productDetailsFragment);
        });
        return binding.getRoot();
    }
}