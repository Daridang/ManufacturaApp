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
import lt.manufaktura.app.databinding.FragmentEditProductPriceBinding;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductEditPriceFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductEditPriceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditProductPriceBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_product_price, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        binding.toEditPictureBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productEditPriceFragment_to_productEditPictureFragment);
        });

        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());
        return binding.getRoot();
    }
}