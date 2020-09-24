package lt.manufaktura.app.model.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import lt.manufaktura.app.Const;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.ProductListItemBinding;
import lt.manufaktura.app.user.account.OnRecyclerViewItemClickListener;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-08-01.
 */
public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private OnRecyclerViewItemClickListener listener;

    private List<Product> products;
    private ProductListItemBinding plib;

    public ProductAdapter(
            List<Product> products,
            OnRecyclerViewItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        plib = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.product_list_item,
                parent,
                false
        );
        return new ProductViewHolder(plib);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = products.get(position);
        plib.setProduct(p);
        plib.getRoot().setOnClickListener(v -> {
            listener.onClick(p.getProductID());
        });
        String picName = null;
        if(p.getProductPicture() != null) {
            picName = p.getProductPicture()
                    .substring(p.getProductPicture().lastIndexOf("/")+1);
        }

        if(picName != null) {
            Glide.with(holder.itemView.getContext())
                    .load(Const.BASE_IMAGE_URL + picName)
                    .fitCenter()
                    .into(plib.productItemImageViewId);
        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ProductViewHolder(@NonNull ProductListItemBinding itemView) {
            super(itemView.getRoot());

        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }
}
