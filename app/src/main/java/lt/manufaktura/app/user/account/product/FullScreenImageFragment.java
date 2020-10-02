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
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.Const;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentFullScreenImageBinding;
import lt.manufaktura.app.model.product.ProductViewModel;

@AndroidEntryPoint
public class FullScreenImageFragment extends Fragment {

    @Inject
    SharedPreferences prefs;

    private ProductViewModel productViewModel;

    public FullScreenImageFragment() {
        // Required empty public constructor
    }

    public static FullScreenImageFragment newInstance() {
        return new FullScreenImageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFullScreenImageBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_full_screen_image, container, false
        );
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        int productId = requireArguments().getInt("productId");
        productViewModel.getProductById("Bearer " + prefs.getString("Token", ""), productId);

        ImageView imageView = binding.fullScreenImgViewId;

        productViewModel.product.observe(getViewLifecycleOwner(), product -> {
            binding.setProduct(product);
            Glide.with(binding.getRoot().getContext())
                    .load(Const.BASE_IMAGE_URL + product.getProductPicture())
                    .fitCenter()
                    .into(binding.fullScreenImgViewId);
        });

        imageView.setOnClickListener(v -> {
            NavDirections navDirections = FullScreenImageFragmentDirections
                    .actionFullScreenImageFragmentToProductDetailsFragment(binding.getProduct().getProductID());
            NavHostFragment
                    .findNavController(this)
                    .navigate(navDirections);
        });

        return binding.getRoot();
    }
}