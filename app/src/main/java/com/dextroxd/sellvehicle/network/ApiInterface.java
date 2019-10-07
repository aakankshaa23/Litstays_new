package com.dextroxd.sellvehicle.network;

import android.content.SharedPreferences;

import com.dextroxd.sellvehicle.network.Login.model.LoginPost;
import com.dextroxd.sellvehicle.network.Login.model.LoginResponse;
import com.dextroxd.sellvehicle.network.Login.model.Response;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response_Submit;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Part;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.facebook.FacebookSdk.getApplicationContext;

public interface ApiInterface
{
    SharedPreferences preferences = getApplicationContext().getSharedPreferences("Litstays",0);
    String authToken = preferences.getString("auth_Token","hell");
    @POST("user/register")
    Call<Response> saveUser(
            @Body Response response
    );

    @POST("user/login")
    Call<LoginResponse> loginUser(
            @Body LoginPost loginPost
    );

    @GET("property/get")
    Call<List<com.dextroxd.sellvehicle.network.PostProperty.model.Response>> getProperty();

    @GET("property/getPostings")
    Call<List<com.dextroxd.sellvehicle.network.PostProperty.model.Response>> getMyProperty(@Header("authToken") String authToken);

    @POST("property/search")
    Call<List<com.dextroxd.sellvehicle.network.PostProperty.model.Response>> searchProperty(
            @Body com.dextroxd.sellvehicle.network.PostOfSearch.Response response );

    @POST("property/addFav")
    Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> addfav(@Header("authToken") String authToken, @Body com.dextroxd.sellvehicle.network.RequestofId.Response response);

    @POST("property/removeFav")
    Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> removeFav(@Header("authToken") String authToken, @Body com.dextroxd.sellvehicle.network.RequestofId.Response response);

    @POST("property/delete")
    Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> removeProperty(@Header("authToken") String authToken, @Body com.dextroxd.sellvehicle.network.RequestofId.Response response);

    @GET("property/getFav")
    Call<List<com.dextroxd.sellvehicle.network.PostProperty.model.Response>> getFav(@Header("authToken") String authToken);

    @POST("property/checkFav")
    Call<com.dextroxd.sellvehicle.network.RequestofId.CheckofFav.Response> checkFav(@Header("authToken") String authToken, @Body com.dextroxd.sellvehicle.network.RequestofId.Response response);



    @Multipart
    @POST("property/submit")
    Call<Response_Submit> submitProperty(@Header("authToken") String authToken,@Part("name") String name, @Part("description") String description, @Part("floors") int floors, @Part("parking") boolean parking, @Part("facing") String facing, @Part MultipartBody.Part[] image,
                                         @Part("type") String type, @Part("bedroom") int bedroom, @Part("bathroom")int bathroom, @Part("furnished") int furnished, @Part("bachelorsAllowed") boolean bachelors, @Part("area") int area, @Part("price") int price, @Part("buildYear") int buildYear,@Part("location") String location);

    @Multipart
    @POST("user/setProfileImage")
    Call<com.dextroxd.sellvehicle.network.GetDetails.Response> addImage(@Header("authToken") String authToken, @Part MultipartBody.Part image);

    @GET("getUserInfo")
    Call<com.dextroxd.sellvehicle.network.GetDetails.Response> getDetails(@Query("id") String UserId);

    @POST("modifyUser")
    Call<com.dextroxd.sellvehicle.network.GetDetails.Response> changeUserDetail(@Header("authToken") String authToken, @Body com.dextroxd.sellvehicle.network.ModelSubmitforChangeofDetails.Response response);

    @POST("createNewPassword")
    Call<com.dextroxd.sellvehicle.network.RequestofId.Message.Response> createNewpassword(@Header("authToken") String authToken, @Body com.dextroxd.sellvehicle.network.PasswordChange.Response response);

    @POST("forgot")
    Call<Response_Submit>  forgetPassword(@Body com.dextroxd.sellvehicle.network.forgetPassword.Response response);

}
