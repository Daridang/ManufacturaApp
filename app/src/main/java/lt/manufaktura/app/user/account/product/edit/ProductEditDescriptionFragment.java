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
import lt.manufaktura.app.databinding.FragmentEditProductDescriptionBinding;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductEditDescriptionFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductEditDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditProductDescriptionBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_product_description, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        binding.toEditPriceBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productEditDescriptionFragment_to_productEditPriceFragment);
        });

        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());
        return binding.getRoot();
    }
}