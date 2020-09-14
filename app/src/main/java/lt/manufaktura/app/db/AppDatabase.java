package lt.manufaktura.app.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductDAO;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-31.
 */
@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDao();
}
