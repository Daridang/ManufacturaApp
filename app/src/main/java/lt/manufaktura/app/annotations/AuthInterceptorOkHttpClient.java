package lt.manufaktura.app.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-07-18.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthInterceptorOkHttpClient {
}
