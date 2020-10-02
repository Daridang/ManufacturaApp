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

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.Const;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentEditProductNameBinding;
import lt.manufaktura.app.databinding.FragmentProductNameBinding;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductEditNameFragment extends Fragment {

    @Inject
    SharedPreferences prefs;
    private ProductViewModel productViewModel;

    public ProductEditNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditProductNameBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_product_name, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        int productId = requireArguments().getInt("productId");
        productViewModel.getProductById("Bearer " + prefs.getString("Token", ""), productId);
        productViewModel.product.observe(getViewLifecycleOwner(), product -> {
            binding.setProduct(product);
        });


        binding.toEditCategoryBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productEditNameFragment_to_productEditCategoryFragment);
        });

        binding.setViewmodel(productViewModel);
        return binding.getRoot();
    }
}