package lt.manufaktura.app.service;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import lt.manufaktura.app.model.product.Product;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-31.
 */
public interface ProductAPI {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("product/getproducts")
    Observable<List<Product>> getProductList(@Header("Authorization") String token);

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("product/getproduct/")
    Observable<Product> getProductById(
            @Header("Authorization") String token,
            @Query("ProductID") int ProductID);

    @POST("Product/AddProduct")
    @FormUrlEncoded
    Single<Integer> addProduct(
            @Header("Authorization") String token,
            @Field("Name") String name,
            @Field("Section") String section,
            @Field("Price") double price,
            @Field("Category") String category,
            @Field("Description") String description,
            @Field("ProductPicture") String picture,
            @Field("ProductImage") String image
    );

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("Product/UpdateProduct")
    @FormUrlEncoded
    Single<Integer> editProduct(
            @Header("Authorization") String token,
            @Field("ProductID") int id,
            @Field("Name") String name,
            @Field("Section") String section,
            @Field("Price") double price,
            @Field("Category") String category,
            @Field("Description") String description,
            @Field("ProductPicture") String picture,
            @Field("ProductImage") String image
    );

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("Product/DeleteProduct/")
    Single<Integer> deleteProduct(@Header("Authorization") String token,
                                  @Query("productID") int productId);
}
