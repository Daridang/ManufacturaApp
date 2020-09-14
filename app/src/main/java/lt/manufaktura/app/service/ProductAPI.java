package lt.manufaktura.app.service;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import lt.manufaktura.app.model.product.Product;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
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

    @GET("product/getproducts")
    Observable<List<Product>> getProductList();

    @POST("Product/AddProduct")
    @FormUrlEncoded
    Single<Integer> addProduct(
            @Field("Name") String name,
            @Field("Section") String section,
            @Field("Price") double price,
            @Field("Category") String category,
            @Field("Description") String description,
            @Field("ProductPicture") String picture
    );

    @Multipart
    @POST("Bandymas/upload")
//    @FormUrlEncoded
    Single<Integer> uploadProduct(
            @Body RequestBody body
//            @Field("Name") String name,
//            @Field("Description") String description,
//            @Field("Section") String section,
//            @Field("Price") double price,
//            @Field("Category") String category,
//            @Field("ProductImage") String picture
    );

    @POST("Product/UpdateProduct")
    @FormUrlEncoded
    Single<Integer> editProduct(
            @Field("ProductID") int id,
            @Field("Name") String name,
            @Field("Section") String section,
            @Field("Price") double price,
            @Field("Category") String category,
            @Field("Description") String description,
            @Field("ProductPicture") String picture
    );

    @POST("Product/DeleteProduct/")
    Single<Integer> deleteProduct(@Query("productId") int productId);
}
