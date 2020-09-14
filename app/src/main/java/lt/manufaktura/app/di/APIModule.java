package lt.manufaktura.app.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import lt.manufaktura.app.service.ProductAPI;
import lt.manufaktura.app.user.login.LoginDataApiService;
import retrofit2.Retrofit;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-08-14.
 */
@Module
@InstallIn(ApplicationComponent.class)
public class APIModule {

    @Provides
    @Singleton
    public static LoginDataApiService provideLogin(Retrofit retrofit) {
        return retrofit.create(LoginDataApiService.class);
    }

    @Provides
    @Singleton
    public static ProductAPI provideProductAPI(Retrofit retrofit) {
        return retrofit.create(ProductAPI.class);
    }
}
