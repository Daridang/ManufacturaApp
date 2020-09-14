package lt.manufaktura.app.di;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import lt.manufaktura.app.db.AppDatabase;
import lt.manufaktura.app.model.product.ProductDAO;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-31.
 */
@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "ManufakturaDB"
        ).build();
    }

    @Provides
    @Singleton
    public ProductDAO provideProductDAO(AppDatabase db) {
        return db.productDao();
    }
}
