package lt.manufaktura.app.user.account.product;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        Log.d("TAGGG", " onCreate ");
        adapter = new ProductAdapter(new ArrayList<>(), this);
        adapter.notifyDataSetChanged();
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
        Log.d("TAGGG", " onCreateView ");
        binding.showDataRecyclerViewId.setAdapter(adapter);

        binding.createProductBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_userProductionFragment_to_productNameFragment);
        });

        productViewModel._isRefreshNeeded.observe(requireActivity(), refresh -> {
            if (refresh) {
                ((ProductAdapter)binding.showDataRecyclerViewId.getAdapter()).clearList();
                productViewModel._isRefreshNeeded.setValue(false);
                productViewModel.getProducts("Bearer " + prefs.getString("Token", ""));
            }
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("TAGGG", " onAttach ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAGGG", " onActivityCreated ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAGGG", " onStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAGGG", " onResume ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAGGG", " onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAGGG", " onStop ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("TAGGG", " onDestroyView ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAGGG", " onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TAGGG", " onDetach ");
    }
}