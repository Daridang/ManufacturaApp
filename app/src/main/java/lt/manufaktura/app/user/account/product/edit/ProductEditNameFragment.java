package lt.manufaktura.app.user.account.product.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentEditProductNameBinding;
import lt.manufaktura.app.databinding.FragmentProductNameBinding;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductEditNameFragment extends Fragment {

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

        binding.toEditCategoryBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productEditNameFragment_to_productEditCategoryFragment);
        });

        binding.setViewmodel(productViewModel);
        Product product = requireArguments().getParcelable("product");
        if (product != null) {
            binding.setProduct(product);
            productViewModel.setProduct(product);
        } else {
            binding.setProduct(productViewModel.getProduct());
        }
        return binding.getRoot();
    }
}