package lt.manufaktura.app.user.account.product.create;

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
import lt.manufaktura.app.databinding.FragmentProductCategoryBinding;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductCategoryFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductCategoryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_product_category, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        binding.toSectionBtnId.setOnClickListener(v -> {
            if (binding.productCategoryInputId.getText().toString().isEmpty()) {
                binding.productCategoryInputId.setError("Enter category");
            } else {
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_productCategoryFragment_to_productSectionFragment);
            }
        });

        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());
        return binding.getRoot();
    }
}