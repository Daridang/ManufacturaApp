package lt.manufaktura.app.repository;

import android.content.SharedPreferences;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductDAO;
import lt.manufaktura.app.service.ProductAPI;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-31.
 */
public class ProductRepository {

    @Inject
    SharedPreferences prefs;

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

    public Observable<List<Product>> getProducts(String token) {
        return productAPI.getProductList(token);
    }

    public Observable<Product> getProductById(String token, int id) {
        return productAPI.getProductById(token, id);
    }

    public Single<Integer> createProduct(String token, Product product) {
        return productAPI.addProduct(
                token,
                product.getName(),
                product.getSection(),
                product.getPrice(),
                product.getCategory(),
                product.getDescription(),
                product.getProductPicture(),
                product.getProductImage()
        );
    }

    public Single<Integer> updateProduct(String token, Product product) {
        return productAPI.addProduct(
                token,
//                product.getProductID(),
                product.getName(),
                product.getSection(),
                product.getPrice(),
                product.getCategory(),
                product.getDescription(),
                product.getProductPicture(),
                product.getProductImage()
        );
    }

    public Single<Integer> deleteProduct(String token, int id) {
        return productAPI.deleteProduct(token, id);
    }
}
