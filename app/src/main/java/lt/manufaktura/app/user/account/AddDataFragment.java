package lt.manufaktura.app.user.account;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentAddDataBinding;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductViewModel;


@AndroidEntryPoint
public class AddDataFragment extends Fragment {

    private ProductViewModel productViewModel;
    private FragmentAddDataBinding fadb;

    @Inject
    SharedPreferences prefs;

    public AddDataFragment() {
        // Required empty public constructor
    }

    public static AddDataFragment newInstance() {
        return new AddDataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.getProducts(prefs.getString("Token", ""));
        productViewModel.products.observe(this, products -> {

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fadb = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_data, container, false
        );

        fadb.productPriceInputId.setFilters(new InputFilter[]{new PriceInputFilter(9, 2)});

        fadb.scannerBtnId.setOnClickListener(v -> {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_addDataFragment_to_scannerActivity);
        });

        Product p = new Product();
        fadb.setProduct(p);
        fadb.setViewmodel(productViewModel);

        fadb.saveDataBtnId.setOnClickListener(v -> {
            save();
        });

        return fadb.getRoot();
    }

    public void save() {

        if (TextUtils.isEmpty(fadb.productNameInputId.getEditableText().toString())
                || TextUtils.isEmpty(fadb.productCategoryInputId.getEditableText().toString()) || TextUtils.isEmpty(fadb.productPriceInputId.getEditableText().toString()) || TextUtils.isEmpty(fadb.productSectionInputId.getEditableText().toString()) || TextUtils.isEmpty(fadb.productDescriptionInputId.getEditableText().toString())) {
            Toast.makeText(getActivity(), "Empty fields", Toast.LENGTH_LONG).show();
            return;
        }

        productViewModel.insertProduct(fadb.getProduct());
//        NavHostFragment
//                .findNavController(this)
//                .navigate(R.id.action_addDataFragment_to_showDataFragment);
    }

    private static class PriceInputFilter implements InputFilter {

        private Pattern _pattern;

        public PriceInputFilter(int digitsBefore, int digitsAfter) {
            _pattern = Pattern.compile("[0-9]{0," + (digitsBefore - 1) + "}+((\\.[0-9]{0," + (digitsAfter - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(
                CharSequence source,
                int start, int end,
                Spanned dest, int dstart, int dend) {

            Matcher matcher = _pattern.matcher(dest);
            if (!matcher.matches()) {
                return "";
            }
            return null;
        }
    }
}