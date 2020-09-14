package lt.manufaktura.app.user.account.product.create;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentProductPriceBinding;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductPriceFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductPriceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProductPriceBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_product_price, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        binding.toPictureBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productPriceFragment_to_productPictureFragment);
        });

        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());
        return binding.getRoot();
    }
}