package lt.manufaktura.app.model.product;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import lt.manufaktura.app.repository.ProductRepository;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-31.
 */
public class ProductViewModel extends ViewModel {

    private CompositeDisposable disposable;

    private final ProductRepository productRepository;

    private MutableLiveData<List<Product>> _productList = new MutableLiveData<>();
    private MutableLiveData<Product> _product = new MutableLiveData<>();

    public LiveData<List<Product>> products = _productList;
    public LiveData<Product> product = _product;

    @ViewModelInject
    public ProductViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
        disposable = new CompositeDisposable();
    }

    public Product getEmptyProduct() {
        return new Product();
    }

    public Product getProduct() {
        return product.getValue();
    }

    public void setProduct(Product product) {
        _product.setValue(product);
    }

    public void getProducts(String token) {
        disposable.add(productRepository.getProducts(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productResponse -> {
                            Log.d("TAGGG", "product response: " + productResponse);
                            _productList.postValue(productResponse);
                        },
                        throwable -> {
                            Log.d("TAGGG", "Throwable: " + throwable.getMessage());
                        })
        );
    }

    public void insertProduct(Product product) {
        Completable.fromAction(() -> productRepository.insert(product))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void createProduct(String token, Product product) {
        disposable.add(productRepository.createProduct(token, product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        v -> {
                            Log.d("TAGGG", "?? " + v.toString());
                        },
                        throwable -> {
                            Log.d("TAGGG", "wtf throwable : " + throwable.getMessage());
                        }
                )
        );
    }

    public void editProduct(String token, Product product) {
        disposable.add(productRepository.updateProduct(token, product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        v -> {
                            Log.d("TAGGG", "edit " + v);
                        },
                        throwable -> {
                            Log.d("TAGGG", "wtfEdit: " + throwable.getMessage());
                        }
                )
        );
    }

    public void deleteProduct(String token, int id) {
        disposable.add(productRepository.deleteProduct(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        v -> {
                            Log.d("TAGGG", "delete " + v);
                        },
                        throwable -> {
                            Log.d("TAGGG", "wtfDelete: " + throwable.getMessage());
                        }
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
