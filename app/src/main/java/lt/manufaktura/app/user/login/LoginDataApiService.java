package lt.manufaktura.app.user.login;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-08-09.
 */
public interface LoginDataApiService {

    @POST("authentication/login")
    @FormUrlEncoded
    Observable<Response<LoginResponse>> login(@FieldMap Map<String, String> params);
}
