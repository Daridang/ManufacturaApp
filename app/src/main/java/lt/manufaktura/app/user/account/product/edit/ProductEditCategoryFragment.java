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
import lt.manufaktura.app.databinding.FragmentEditProductCategoryBinding;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class ProductEditCategoryFragment extends Fragment {

    private ProductViewModel productViewModel;

    public ProductEditCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditProductCategoryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_product_category, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        binding.toEditSectionBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productEditCategoryFragment_to_productEditSectionFragment);
        });

        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());
        return binding.getRoot();
    }
}