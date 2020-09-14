package lt.manufaktura.app.user.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.chart.common.dataentry.TreeDataEntry;

import org.jetbrains.annotations.NotNull;

import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_account, container, false
        );

        NavHostFragment navHostFragment = (NavHostFragment)
                getChildFragmentManager().findFragmentById(R.id.account_nav_host_fragment);

        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.accountBottomNavId, controller);

//
//        AnyChartView anyChartView = flb.anychartId;
//
//        TreeMap treeMap = AnyChart.treeMap();
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new CustomTreeDataEntry("Products by Revenue", null, "Products by Revenue"));
//        data.add(new CustomTreeDataEntry("Fruits", "Products by Revenue", "Fruits", 692000));
//        data.add(new CustomTreeDataEntry("Vegetables", "Products by Revenue", "Vegetables", 597000));
//        data.add(new CustomTreeDataEntry("Dairy", "Products by Revenue", "Dairy", 1359000));
//        data.add(new CustomTreeDataEntry("Meat", "Products by Revenue", "Meat", 596000));
//        data.add(new CustomTreeDataEntry("Apples", "Fruits", "Apples", 138000));
//        data.add(new CustomTreeDataEntry("Oranges", "Fruits", "Oranges", 22000));
//        data.add(new CustomTreeDataEntry("Bananas", "Fruits", "Bananas", 88000));
//        data.add(new CustomTreeDataEntry("Melons", "Fruits", "Melons", 77000));
//        data.add(new CustomTreeDataEntry("Apricots", "Fruits", "Apricots", 48000));
//        data.add(new CustomTreeDataEntry("Plums", "Fruits", "Plums", 48000));
//        data.add(new CustomTreeDataEntry("Pineapples", "Fruits", "Pineapples", 41000));
//        data.add(new CustomTreeDataEntry("Cherries", "Fruits", "Cherries", 39000));
//        data.add(new CustomTreeDataEntry("Tangerines", "Fruits", "Tangerines", 32000));
//        data.add(new CustomTreeDataEntry("Potato", "Vegetables", "Potato", 189000));
//        data.add(new CustomTreeDataEntry("Eggplants", "Vegetables", "Eggplants", 94000));
//        data.add(new CustomTreeDataEntry("Tomatoes", "Vegetables", "Tomatoes", 63000));
//        data.add(new CustomTreeDataEntry("Cucumbers", "Vegetables", "Cucumbers", 43000));
//        data.add(new CustomTreeDataEntry("Cabbage", "Vegetables", "Cabbage", 30000));
//        data.add(new CustomTreeDataEntry("Carrot", "Vegetables", "Carrot", 29000));
//        data.add(new CustomTreeDataEntry("Squash", "Vegetables", "Squash", 26000));
//        data.add(new CustomTreeDataEntry("Capsicums", "Vegetables", "Capsicums", 23000));
//        data.add(new CustomTreeDataEntry("Milk", "Dairy", "Milk", 154000));
//        data.add(new CustomTreeDataEntry("Curd", "Dairy", "Curd", 142000));
//        data.add(new CustomTreeDataEntry("Cheese", "Dairy", "Cheese", 43000));
//        data.add(new CustomTreeDataEntry("Yogurt", "Dairy", "Yogurt", 38000));
//        data.add(new CustomTreeDataEntry("Kefir", "Dairy", "Kefir", 32000));
//        data.add(new CustomTreeDataEntry("Mutton", "Meat", "Mutton", 154000));
//        data.add(new CustomTreeDataEntry("Beef", "Meat", "Beef", 142000));
//        data.add(new CustomTreeDataEntry("Pork", "Meat", "Pork", 43000));
//        data.add(new CustomTreeDataEntry("Veal", "Meat", "Veal", 38000));
//
//        treeMap.data(data, TreeFillingMethod.AS_TABLE);
//
//        Title title = treeMap.title();
//        title.enabled(true);
//        title.useHtml(true);
//        title.padding(0d, 0d, 20d, 0d);
//        title.text("Top ACME Products by Revenue<br/>' +\n" +
//                "      '<span style=\"color:#212121; font-size: 13px;\">(average sales during the year, in $)</span>");
//
//        treeMap.colorScale().ranges(new String[]{
//                "{ less: 25000 }",
//                "{ from: 25000, to: 30000 }",
//                "{ from: 30000, to: 40000 }",
//                "{ from: 40000, to: 50000 }",
//                "{ from: 50000, to: 100000 }",
//                "{ greater: 100000 }"
//        });
//
//        treeMap.colorScale().colors(new String[]{
//                "#ffee58", "#fbc02d", "#f57f17", "#c0ca33", "#689f38", "#2e7d32"
//        });
//
//        treeMap.padding(10d, 10d, 10d, 20d);
//        treeMap.maxDepth(2d);
//        treeMap.hovered().fill("#bdbdbd", 1d);
//        treeMap.selectionMode(SelectionMode.NONE);
//
//        treeMap.legend().enabled(true);
//        treeMap.legend()
//                .padding(0d, 0d, 0d, 20d)
//                .position(Orientation.RIGHT)
//                .align(Align.TOP)
//                .itemsLayout(LegendLayout.VERTICAL);
//
//        treeMap.labels().useHtml(true);
//        treeMap.labels().fontColor("#212121");
//        treeMap.labels().fontSize(12d);
//        treeMap.labels().format(
//                "function() {\n" +
//                        "      return this.getData('product');\n" +
//                        "    }");
//
//        treeMap.headers().format(
//                "function() {\n" +
//                        "    return this.getData('product');\n" +
//                        "  }");
//
//        treeMap.tooltip()
//                .useHtml(true)
//                .titleFormat("{%product}")
//                .format("function() {\n" +
//                        "      return '<span style=\"color: #bfbfbf\">Revenue: </span>$' +\n" +
//                        "        anychart.format.number(this.value, {\n" +
//                        "          groupsSeparator: ' '\n" +
//                        "        });\n" +
//                        "    }");
//
//        anyChartView.setChart(treeMap);

//        binding.accountBottomNavId.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.show_data_menu_item_id:
//                    NavHostFragment
//                            .findNavController(this)
//                            .navigate(R.id.action_accountFragment_to_showDataFragment);
//                    break;
//                case R.id.create_entity_menu_item_id:
//                    NavHostFragment
//                            .findNavController(this)
//                            .navigate(R.id.action_accountFragment_to_createEntityFragment);
//                    break;
//                case R.id.album_menu_item_id:
//                    NavHostFragment
//                            .findNavController(this)
//                            .navigate(R.id.action_accountFragment_to_albumFragment);
//                    break;
//                case R.id.user_production_menu_item_id:
//                    NavHostFragment
//                            .findNavController(this)
//                            .navigate(R.id.action_accountFragment_to_userProductionFragment);
//                    break;
//            }
//            return true;
//        });

//        binding.showDataBtnId.setOnClickListener(v ->
//                NavHostFragment
//                        .findNavController(this)
//                        .navigate(R.id.action_accountFragment_to_showDataFragment)
//        );
//
//        binding.enterDataBtnId.setOnClickListener(v -> {
////            NavHostFragment
////                    .findNavController(this)
////                    .navigate(R.id.action_accountFragment_to_addDataFragment);
//            NavHostFragment
//                    .findNavController(this)
//                    .navigate(R.id.action_accountFragment_to_createEntityFragment);
//        });
//
//        binding.albumBtnId.setOnClickListener(v -> {
//            NavHostFragment
//                    .findNavController(this)
//                    .navigate(R.id.action_accountFragment_to_albumFragment);
//        });

        return binding.getRoot();
    }

    private static class CustomTreeDataEntry extends TreeDataEntry {
        CustomTreeDataEntry(String id, String parent, String product, Integer value) {
            super(id, parent, value);
            setValue("product", product);
        }

        CustomTreeDataEntry(String id, String parent, String product) {
            super(id, parent);
            setValue("product", product);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}