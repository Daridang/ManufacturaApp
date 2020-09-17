package lt.manufaktura.app.user.account.product;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentProductDetailsBinding;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductViewModel;

@AndroidEntryPoint
public class ProductDetailsFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductDetailsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_product_details, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        productViewModel.product.observe(getViewLifecycleOwner(), (binding::setProduct));

        binding.setProduct(requireArguments().getParcelable("product"));
        binding.deleteProductDetailsBtnId.setOnClickListener(v -> {
            productViewModel.deleteProduct(binding.getProduct().getProductID());
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productDetailsFragment_to_userProductionFragment);
        });

        binding.editProductDetailsBtnId.setOnClickListener(v -> {
            NavDirections navDirections =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductEditNameFragment(binding.getProduct());

            NavHostFragment
                    .findNavController(this)
                    .navigate(navDirections);
        });

        binding.toolbarId.setNavigationOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productDetailsFragment_to_userProductionFragment);
        });

        return binding.getRoot();
    }
}