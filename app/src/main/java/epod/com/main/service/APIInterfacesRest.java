package epod.com.main.service;

/**
 * Created by user on 1/10/2018.
 */



import epod.com.main.datamodel.DetailOrder.UpdateOrder;
import epod.com.main.datamodel.ModelLogin.Authentication;
import epod.com.main.datamodel.ModelOrder.ModelOrder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by anupamchugh on 09/01/17.
 */

 public interface APIInterfacesRest {


    @FormUrlEncoded
    @POST("api/user/login")
    Call<Authentication> getAuthentication(@Field("username") String username, @Field("password") String password);


    @GET("api/dataorder/all")
    Call<ModelOrder> getOrder( @Query("username") String user);


   @Multipart
   @POST("api/detailorder/add")
   Call<UpdateOrder> updateData(

           @Part("orderno") RequestBody orderno,
           @Part("shipto") RequestBody shipto,
           @Part("shipname") RequestBody shipname,
           @Part("location") RequestBody location,
           @Part("recieveddate") RequestBody recieveddate,
           @Part("poddate") RequestBody poddate,


           @Part MultipartBody.Part img1,
           @Part MultipartBody.Part img2,
           @Part MultipartBody.Part img3,

           @Part("status") RequestBody status,
           @Part("verification") RequestBody verification
   );



}

