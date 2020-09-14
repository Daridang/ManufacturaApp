//package lt.manufaktura.app.user.account;
//
//import android.os.Bundle;
//
//import androidx.databinding.DataBindingUtil;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//
//import dagger.hilt.android.AndroidEntryPoint;
//import lt.manufaktura.app.R;
//import lt.manufaktura.app.databinding.FragmentShowDataBinding;
//import lt.manufaktura.app.model.product.Product;
//import lt.manufaktura.app.model.product.ProductAdapter;
//import lt.manufaktura.app.model.product.ProductViewModel;
//
//
//@AndroidEntryPoint
//public class ShowDataFragment extends Fragment implements OnDeleteClickListener {
//
//    private ProductViewModel productViewModel;
//    private FragmentShowDataBinding fsdb;
//    private ProductAdapter adapter;
//
//    public ShowDataFragment() {
//        // Required empty public constructor
//    }
//
//    public static ShowDataFragment newInstance() {
//        return new ShowDataFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        adapter = new ProductAdapter(new ArrayList<>());
//        adapter.setListener(this);
//        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
//        productViewModel.getProducts();
//        productViewModel.products.observe(this, products -> {
//            adapter.setProducts(products);
//            adapter.notifyDataSetChanged();
//        });
//    }
//
//    @Override
//    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        fsdb = DataBindingUtil.inflate(
//                inflater, R.layout.fragment_show_data, container, false
//        );
//
//        fsdb.showDataRecyclerViewId.setAdapter(adapter);
//        return fsdb.getRoot();
//    }
//
//    @Override
//    public void onDelete(Product product) {
//        productViewModel.deleteProduct(product);
//    }
//}