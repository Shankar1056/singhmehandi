package apextechies.singhmehandi.network;


import apextechies.singhmehandi.login.model.LoginModel;
import apextechies.singhmehandi.login.model.LoginModelVerifyModel;
import apextechies.singhmehandi.login.model.OtpRequest;
import apextechies.singhmehandi.login.model.OtpValidate;
import io.reactivex.Observable;
import retrofit2.http.*;

/**
 * Created by anujgupta on 26/12/17.
 */

public interface NetworkInterface {

    @POST(BaseUrl.LOGINVALIDATEAPI)
    Observable<LoginModel> userLogin(@Body OtpRequest request);

    @POST(BaseUrl.LOGINVALIDATEAPI)
    Observable<LoginModelVerifyModel> verifyOtp(@Body OtpValidate request);

    @GET("search/movie")
    Observable<LoginModel> getMoviesBasedOnQuery(@Query("api_key") String api_key, @Query("query") String q);

    @Headers("x-api-key:1a!2b@3c#4d$5e%6f^")
    @POST("search/movie")
    Observable<LoginModel> getMoviesBasedOnQuery(@Field("user_id") String user_id, @Field("password") String password, @Field("school_id") String school_id, @Field("user_type") String user_type);
}
