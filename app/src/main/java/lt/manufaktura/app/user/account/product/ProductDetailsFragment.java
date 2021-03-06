package lt.manufaktura.app.user.account.product;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;


import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.Const;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentProductDetailsBinding;
import lt.manufaktura.app.model.product.ProductViewModel;

@AndroidEntryPoint
public class ProductDetailsFragment extends Fragment {

    @Inject
    SharedPreferences prefs;

    private ProductViewModel productViewModel;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductDetailsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_product_details, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        int productId = requireArguments().getInt("productId");
        productViewModel.getProductById("Bearer " + prefs.getString("Token", ""), productId);

        productViewModel.product.observe(getViewLifecycleOwner(), product -> {
            binding.setProduct(product);
            Glide.with(binding.getRoot().getContext())
                    .load(Const.BASE_IMAGE_URL + product.getProductPicture())
                    .fitCenter()
                    .into(binding.singleProductImageViewId);
        });

        binding.deleteProductDetailsBtnId.setOnClickListener(v -> {
            productViewModel.deleteProduct("Bearer " + prefs.getString("Token", ""),
                    binding.getProduct().getProductID());
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productDetailsFragment_to_userProductionFragment);
        });

//        binding.editProductDetailsBtnId.setOnClickListener(v -> {
//            NavDirections navDirections =
//                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductNameFragment(binding.getProduct().getProductID());
//
//            NavHostFragment
//                    .findNavController(this)
//                    .navigate(navDirections);
//        });

        binding.toolbarId.setNavigationOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productDetailsFragment_to_userProductionFragment);
        });

        binding.singleProductImageViewId.setOnClickListener(v -> {
            NavDirections navDirections =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToFullScreenImageFragment(
                            binding.getProduct().getProductID());
            NavHostFragment
                    .findNavController(this)
                    .navigate(navDirections);
        });
        return binding.getRoot();
    }
}