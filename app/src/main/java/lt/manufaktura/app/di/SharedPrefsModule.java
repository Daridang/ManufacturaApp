package lt.manufaktura.app.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-08-10.
 */
@Module
@InstallIn(ApplicationComponent.class)
public class SharedPrefsModule {

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPrefsEditor(@ApplicationContext Context context) {
        return context.getSharedPreferences("Prefs", 0);
    }
}
