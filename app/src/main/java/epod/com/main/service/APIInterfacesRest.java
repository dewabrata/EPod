package epod.com.main.service;

/**
 * Created by user on 1/10/2018.
 */



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

    /*
   @Multipart
   @POST("api/tbl_order/update")
   Call<UpdateOrder> updateData(
           @Part("id") RequestBody id,
           @Part("no_psb") RequestBody no_psb,
           @Part("nama") RequestBody nama,
           @Part("alamat1") RequestBody alamat1,
           @Part("alamat2") RequestBody alamat2,
           @Part("kodepos") RequestBody kodepos,
           @Part("kota") RequestBody kota,
           @Part("area") RequestBody area,
           @Part("ke_awal") RequestBody ke_awal,
           @Part("ke_akhir") RequestBody ke_akhir,
           @Part("tgl_jtempo1") RequestBody tgl_jtempo1,
           @Part("tgl_jtempo2") RequestBody tgl_jtempo2,
           @Part("kode_ao") RequestBody kode_ao,
           @Part("nama_ao") RequestBody nama_ao,
           @Part("jml_angsuran") RequestBody jml_angsuran,
           @Part("jml_denda") RequestBody jml_denda,
           @Part("jml_bayar_tagih") RequestBody jml_bayar_tagih,
           @Part("jml_tot_terbayar") RequestBody jml_tot_terbayar,
           @Part("tgl_bayar") RequestBody tgl_bayar,
           @Part("jam_bayar") RequestBody jam_bayar,
           @Part("longitude") RequestBody longitude,
           @Part("langitude") RequestBody langitude,
           @Part("discount") RequestBody discount,
           @Part("no_kwitansi") RequestBody no_kwitansi,
           @Part("jenis") RequestBody jenis,
           @Part("status") RequestBody status,
           @Part("print_status") RequestBody print_status,
           @Part("reprint_status") RequestBody reprint_status,
           @Part("sisa_angsuran") RequestBody sisa_angsuran,
           @Part("jumlah_sisa") RequestBody jumlah_sisa,
           @Part("kecamatan") RequestBody kecamatan,
           @Part("kelurahan") RequestBody kelurahan,
           @Part("jml_sisa") RequestBody jml_sisa,
           @Part("que") RequestBody que,
           @Part("janji_bayar") RequestBody janji_bayar,
           @Part("alasan") RequestBody alasan,
           @Part("up_data") RequestBody up_data,
           @Part("download_pertanyaan") RequestBody download_pertanyaan,
           @Part("status_bayar") RequestBody status_bayar,
           @Part("nopol") RequestBody nopol,
           @Part("telepon") RequestBody telepon,
           @Part("posko") RequestBody posko,
           @Part("dendasisa") RequestBody dendasisa,
           @Part("angsisa") RequestBody angsisa,
           //    @Part("photo_bayar\"; filename=\"image.jpeg\"") RequestBody photo_bayar,
           @Part MultipartBody.Part photo_bayar,
           @Part("type_kendaraan") RequestBody typeKendaraan,
           @Part("byr_denda") RequestBody byrDenda,
           @Part("byr_sisa") RequestBody byrSisa,
           @Part("Tenor") RequestBody Tenor,
           @Part("jumlahangsuran") RequestBody jumlahangsuran,
           @Part("jml_angsuran_ke") RequestBody jml_angsuran_ke
   );
*/


}

