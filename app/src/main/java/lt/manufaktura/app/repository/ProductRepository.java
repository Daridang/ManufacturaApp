package lt.manufaktura.app.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductDAO;
import lt.manufaktura.app.service.ProductAPI;
import okhttp3.RequestBody;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-31.
 */
public class ProductRepository {

    private ProductDAO productDAO;
    private ProductAPI productAPI;

    @Inject
    public ProductRepository(ProductDAO productDAO, ProductAPI productAPI) {
        this.productDAO = productDAO;
        this.productAPI = productAPI;
    }

    public void insert(Product product) {
        productDAO.insertProduct(product);
    }

    public void update(Product product) {
        productDAO.updateProduct(product);
    }

    public void delete(Product product) {
        productDAO.deleteProduct(product);
    }

    public Observable<List<Product>> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public Observable<List<Product>> getProducts() {
        return productAPI.getProductList();
    }

    public Single<Integer> uploadProduct(RequestBody product) {
        return productAPI.uploadProduct(product);
    }

    public Single<Integer> createProduct(Product product) {
        return productAPI.addProduct(
                product.getName(),
                product.getSection(),
                product.getPrice(),
                product.getCategory(),
                product.getDescription(),
                product.getProductPicture()
        );
    }

    public Single<Integer> updateProduct(Product product) {
        return productAPI.editProduct(
                product.getProductID(),
                product.getName(),
                product.getSection(),
                product.getPrice(),
                product.getCategory(),
                product.getDescription(),
                product.getProductPicture()
        );
    }

    public Single<Integer> deleteProduct(int id) {
        return productAPI.deleteProduct(id);
    }
}
