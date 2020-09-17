package lt.manufaktura.app.user.account.product;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentUserProductionBinding;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductAdapter;
import lt.manufaktura.app.model.product.ProductViewModel;
import lt.manufaktura.app.user.account.OnRecyclerViewItemClickListener;

@AndroidEntryPoint
public class UserProductionFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private ProductViewModel productViewModel;
    private FragmentUserProductionBinding binding;
    private ProductAdapter adapter;

    public UserProductionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProductAdapter(new ArrayList<>(), this);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        productViewModel.getProducts();
        productViewModel.products.observe(this, products -> {
            adapter.setProducts(products);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user_production, container, false
        );

        binding.showDataRecyclerViewId.setAdapter(adapter);

        binding.createProductBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_userProductionFragment_to_productNameFragment);
        });
//
//        binding.toolbarId.setNavigationOnClickListener(v -> {
//            Log.d("TAGG", "Clicked user production fragment");
//        });

        return binding.getRoot();
    }

    @Override
    public void onClick(Product product) {
        NavDirections navDirections =
                UserProductionFragmentDirections.actionUserProductionFragmentToProductDetailsFragment(product);

        NavHostFragment
                .findNavController(this)
                .navigate(navDirections);
    }
}