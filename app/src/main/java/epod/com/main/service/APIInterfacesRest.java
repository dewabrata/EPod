package epod.com.main.service;

/**
 * Created by user on 1/10/2018.
 */



import epod.com.main.datamodel.DetailOrder.UpdateOrder;
import epod.com.main.datamodel.MobileUser.MobileUser;
import epod.com.main.datamodel.ModelLogin.Authentication;

import epod.com.main.datamodel.NewOrder.ModelOrder;
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
    Call<ModelOrder> getOrder(@Query("username") String user);

   @GET("api/user_mobile/all")
   Call<MobileUser> getUser(@Query("username") String user);

/*
            @Part MultipartBody.Part img1,
           @Part MultipartBody.Part img2,
           @Part MultipartBody.Part img3,
 */
   @Multipart
   @POST("api/dataorder/update")
   Call<UpdateOrder> updateData(

                   @Part("pod_date") RequestBody podate,
                   @Part("status") RequestBody status,
                   @Part("lat") RequestBody lat,
                   @Part("lon") RequestBody lon,
                   @Part("poddate") RequestBody poddate,
                   @Part("recievedate") RequestBody recievedate,
                   @Part("id") RequestBody id



           );


   @Multipart
   @POST("api/orderimage/add")
   Call<UpdateOrder> sendImage(

           @Part("orderno") RequestBody orderno,
           @Part("shipno") RequestBody shipno,
           @Part("username") RequestBody username,
           @Part MultipartBody.Part img1,
           @Part("waktu") RequestBody time,
           @Part("id_ref") RequestBody idref

   );

}

