package epod.com.main.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.rxgps.RxGps;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ImgLoader;
import com.pizidea.imagepicker.UilImgLoader;
import com.pizidea.imagepicker.Util;
import com.pizidea.imagepicker.bean.ImageItem;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import epod.com.main.R;
import epod.com.main.application.AppController;

import epod.com.main.datamodel.DetailOrder.UpdateOrder;

import epod.com.main.datamodel.NewOrder.Dataorder;
import epod.com.main.service.APIClient;
import epod.com.main.service.APIInterfacesRest;
import epod.com.main.utils.AppUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static epod.com.main.application.AppController.MY_PERMISSIONS_REQUEST_CAMERA;
import static epod.com.main.utils.AppUtil.Now;

public class DetailOrderActivity extends AppCompatActivity {

    //image multi

    private static final String TAG = DetailOrderActivity.class.getSimpleName();

    private final int REQ_IMAGE = 1433;

    private int counterImage =0;





    ImgLoader presenter = new UilImgLoader();
    GridView mGridView;
    SelectAdapter mAdapter;

    private int screenWidth;

    public List<ImageItem> images;


    ////
    public Uri fileUri;


    private Button btnImg1 ;
    private Button btnSubmit, btnCancel;
    private EditText orderno,shipto,shipname,txtlocation,recievedate,poddate;

    private String shipNo, orderNo;

    public Dataorder datax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        Camerapermission();

        shipNo = getIntent().getStringExtra("shipno");
        orderNo = getIntent().getStringExtra("orderno");


        orderno = (EditText) findViewById(R.id.orderno);
        shipto = (EditText) findViewById(R.id.shipto);
        shipname = (EditText) findViewById(R.id.shipname);
        txtlocation = (EditText) findViewById(R.id.location);
        recievedate = (EditText) findViewById(R.id.recievedate);
        poddate = (EditText) findViewById(R.id.poddate);

        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        mGridView = (GridView) findViewById(R.id.gridview);
        mAdapter = new SelectAdapter(getApplicationContext());
        mGridView.setAdapter(mAdapter);

        setupData();
        setDisable();



        btnImg1 = (Button) findViewById(R.id.btnImg1);

        btnSubmit = (Button) findViewById(R.id.submit);
        btnCancel = (Button) findViewById(R.id.cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (images!=null ) {
                    if (images.size()>0) {
                        counterImage = 0;
                        sendsaveOrder();
                    }else{
                        Toast.makeText(getApplicationContext(),"Mohon foto document harus lebih dari satu!",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Mohon foto document terlebih dahulu!",Toast.LENGTH_LONG).show();
                }

            }
        });


        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AndroidImagePicker.getInstance().pickMulti(DetailOrderActivity.this, true, new AndroidImagePicker.OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(List<ImageItem> items) {
                        if(items != null && items.size() > 0){
                            Log.i(TAG,"=====>data："+items.get(0).path);
                            images = items;
                            mAdapter.clear();
                            mAdapter.addAll(items);

                        }
                    }
                });

/*
                Intent intent = new Intent();
                int requestCode = REQ_IMAGE;
                intent.setClass(DetailOrderActivity.this,ImagesGridActivity.class);
                startActivityForResult(intent, requestCode);*/

            }
        });

    }



    public void setDisable(){
        orderno.setEnabled(false);
        shipto.setEnabled(false);
        shipname.setEnabled(false);
        txtlocation.setEnabled(false);
        recievedate.setEnabled(false);
        poddate.setEnabled(false);
    }

    private void Camerapermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(DetailOrderActivity.this
                ,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(DetailOrderActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(DetailOrderActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void setupData(){

        String rawQuery = "SELECT  * FROM `Dataorder` where  driver ='"+AppController.username+"' and shipmentNo ='"+shipNo+"' and orderNo ='"+orderNo+"' order by shipmentNo";
        StringQuery<Dataorder> stringQuery = new StringQuery<>(Dataorder.class, rawQuery);
        stringQuery
                .async()
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@android.support.annotation.NonNull Transaction transaction, @android.support.annotation.NonNull Throwable error) {

                    }
                })
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Dataorder>() {
                                             @Override
                                             public void onListQueryResult(QueryTransaction transaction, @NonNull List<Dataorder> models) {


                                                if (models.size()>0){
                                                    orderno.setText(models.get(0).getOrderNo());
                                                    shipto.setText(models.get(0).getShipTo());
                                                    shipname.setText(models.get(0).getShipName());
                                                    if (models.get(0).getRecievedate().equalsIgnoreCase("")){
                                                        recievedate.setText(AppUtil.Now());
                                                        models.get(0).setRecievedate(recievedate.getText().toString());
                                                    }else{
                                                        recievedate.setText( models.get(0).getRecievedate());
                                                    }

                                                    if (models.get(0).getPoddate().equalsIgnoreCase("")){
                                                        poddate.setText(AppUtil.Now());
                                                        models.get(0).setPoddate(poddate.getText().toString());
                                                    }else{
                                                        poddate.setText( models.get(0).getPoddate());
                                                    }



                                                    datax = models.get(0);
                                                    datax.save();
                                                    setGPS();

                                                }

                                             }


                                         }


                )
                .execute();

    }




    public void setGPS(){
        final RxGps rxGps = new RxGps(this);

        rxGps.lastLocation()


                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(location -> {
                    // .setLongitude(String.valueOf(location.getLongitude()));
                    //order.setLangitude(String.valueOf(location.getLatitude()));

                    String latlon = String.valueOf(location.getLongitude()) + String.valueOf(location.getLatitude());
                    txtlocation.setText(latlon);
                    datax.setLat(String.valueOf(location.getLatitude()));
                    datax.setLon(String.valueOf(location.getLongitude()));

                }, throwable -> {
                    if (throwable instanceof RxGps.PermissionException) {
                        displayError(throwable.getMessage());
                    } else if (throwable instanceof RxGps.PlayServicesNotAvailableException) {
                        displayError(throwable.getMessage());
                    }
                });
    }

    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




    public Bitmap getBitmap  (File file){

        Bitmap bitmap = null;

        FileInputStream streamIn = null;
        try {
            streamIn = new FileInputStream(file);


            bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image

            streamIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

       return bitmap;
    }

    ProgressDialog progressDialog;


    public void sendsaveOrder(){



        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        //  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);




        progressDialog = new ProgressDialog(DetailOrderActivity.this);
        progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<UpdateOrder> updateService = apiInterface.updateData(
                toRequestBody(AppUtil.replaceNull(datax.getPoddate()).trim()),
                toRequestBody(AppUtil.replaceNull("1")),
                toRequestBody(AppUtil.replaceNull(datax.getLat() ).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getLon() ).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getPoddate()).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getRecievedate() ).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getId() ).trim())




        );
        updateService.enqueue(new Callback<UpdateOrder>() {
            @Override
            public void onResponse(Call<UpdateOrder> call, Response<UpdateOrder> response) {

                if (response != null) {
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.d("Test", response.message());

                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();

                            datax.setStatus("1");
                            datax.save();

                            sendImageProcess();


                        }
                    }
                }else{

                    datax.setStatus("pending");
                    datax.save();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                finish();

            }

            @Override
            public void onFailure(Call<UpdateOrder> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah", Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
                datax.setStatus("pending");
                finish();
            }
        });


    }


    public RequestBody toRequestBody(String value) {
        if (value==null){
            value="";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }


    class SelectAdapter extends ArrayAdapter<ImageItem> {

        //private int mResourceId;
        public SelectAdapter(Context context) {
            super(context, 0);
            //this.mResourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageItem item = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            //View view = inflater.inflate(mResourceId, null);
            int width = (screenWidth - Util.dp2px(DetailOrderActivity.this,10*2))/3;

            ImageView imageView = new ImageView(DetailOrderActivity.this);
            imageView.setBackgroundColor(Color.GRAY);
            GridView.LayoutParams params = new AbsListView.LayoutParams(width, width);
            imageView.setLayoutParams(params);

            presenter.onPresentImage(imageView,item.path,width);

            return imageView;
        }

    }


    public void sentImage(String gambar,String namagambar){

        byte[] bImg1  = AppUtil.FiletoByteArray(new File(gambar));

        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),compressCapture(bImg1,900));
      /*  MultipartBody.Part bodyImg1 =
                MultipartBody.Part.createFormData("img1", datax.getOrderno()+NowX()+".jpg", requestFile1);
*/
        MultipartBody.Part bodyImg1 =
                MultipartBody.Part.createFormData("gambar", namagambar, requestFile1);

        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        //  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);





        Call<UpdateOrder> updateService = apiInterface.sendImage(
                toRequestBody(AppUtil.replaceNull(datax.getOrderNo()).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getShipTo() ).trim()),
                toRequestBody(AppUtil.replaceNull(AppController.username.trim())),
                bodyImg1,
                toRequestBody(AppUtil.replaceNull(Now())),
                        toRequestBody(AppUtil.replaceNull(datax.getId()).trim())


        );

        updateService.enqueue(new Callback<UpdateOrder>() {
            @Override
            public void onResponse(Call<UpdateOrder> call, Response<UpdateOrder> response) {
                counterImage +=1;
                if (response != null) {
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.d("Image Send", response.message());



                            if (counterImage >= images.size()){

                                finish();

                            }



                        }
                    }
                }else{


                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }



            }

            @Override
            public void onFailure(Call<UpdateOrder> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah", Toast.LENGTH_LONG).show();


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK){
            if (requestCode == REQ_IMAGE) {

                List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
                mAdapter.clear();
                mAdapter.addAll(imageList);
                images =imageList;
            }/*else if(requestCode == REQ_IMAGE_CROP){
                Bitmap bmp = (Bitmap)data.getExtras().get("bitmap");
                Log.i(TAG,"-----"+bmp.getRowBytes());
            }*/
        }

    }

    @Override
    protected void onDestroy() {
        //AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().onDestroy();

        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
        super.onDestroy();
    }

    public void sendImageProcess(){



        if(images !=null){
            if (images.size()>0){

                for(int x=0; x <images.size();x++){
                    sentImage(images.get(x).path,images.get(x).name);

                }

            }
        }

    }


    public static byte[] compressCapture(byte[] capture, int maxSizeKB) {

        // This should be different based on the original capture size
        int compression = 12;

        Bitmap bitmap  = BitmapFactory.decodeByteArray(capture, 0, capture.length);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, compression, outputStream);
        return outputStream.toByteArray();
    }

}
