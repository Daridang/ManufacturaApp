package lt.manufaktura.app.user.account.product.create;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentProductNameBinding;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductNameFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductNameBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_product_name, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        binding.toCategoryBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productNameFragment_to_productCategoryFragment);
        });

        binding.setViewmodel(productViewModel);
        Product product = requireArguments().getParcelable("product");
        if (product != null) {
            binding.setProduct(product);
            productViewModel.setProduct(product);
        } else {
            binding.setProduct(productViewModel.getEmptyProduct());
            productViewModel.setProduct(binding.getProduct());
        }
        return binding.getRoot();
    }
}