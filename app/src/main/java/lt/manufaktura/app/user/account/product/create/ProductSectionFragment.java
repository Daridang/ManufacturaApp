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
import lt.manufaktura.app.databinding.FragmentProductSectionBinding;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductSectionFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductSectionBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_product_section, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        binding.toDescriptionBtnId.setOnClickListener(v -> {
            if (binding.productSectionInputId.getText().toString().isEmpty()) {
                binding.productSectionInputId.setError("Enter section");
            } else {
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_productSectionFragment_to_productDescriptionFragment);
            }
        });

        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());
        return binding.getRoot();
    }
}