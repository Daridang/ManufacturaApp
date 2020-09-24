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

import java.util.ArrayList;

import javax.inject.Inject;

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

    @Inject
    SharedPreferences prefs;

    public UserProductionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProductAdapter(new ArrayList<>(), this);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        productViewModel.getProducts("Bearer " + prefs.getString("Token", ""));
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
        return binding.getRoot();
    }

    @Override
    public void onClick(int id) {
        NavDirections navDirections =
                UserProductionFragmentDirections.actionUserProductionFragmentToProductDetailsFragment(id);

        NavHostFragment
                .findNavController(this)
                .navigate(navDirections);
    }
}