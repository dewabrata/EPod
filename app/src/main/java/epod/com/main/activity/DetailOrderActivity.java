package epod.com.main.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.rxgps.RxGps;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import epod.com.main.R;
import epod.com.main.application.AppController;
import epod.com.main.datamodel.DetailOrder.Detailorder;
import epod.com.main.datamodel.DetailOrder.UpdateOrder;
import epod.com.main.datamodel.ModelOrder.Dataorder;
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
import static epod.com.main.utils.AppUtil.NowX;

public class DetailOrderActivity extends AppCompatActivity {

    public Uri fileUri;
    private int CAMERA_REQUEST1 = 11;
    private int CAMERA_REQUEST2 = 22;
    private int CAMERA_REQUEST3 = 33;

    private int Gallary_REQUEST = 101;

    private Button btnImg1 , btnImg2, btnImg3;
    private Button btnSubmit, btnCancel;
    private ImageView img1, img2, img3;
    private EditText orderno,shipto,shipname,txtlocation,recievedate,poddate;

    private String shipNo, orderNo;

    public Detailorder datax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        shipNo = getIntent().getStringExtra("shipno");
        orderNo = getIntent().getStringExtra("orderno");


        orderno = (EditText)findViewById(R.id.orderno);
        shipto = (EditText)findViewById(R.id.shipto);
        shipname = (EditText)findViewById(R.id.shipname);
        txtlocation = (EditText)findViewById(R.id.location);
        recievedate = (EditText)findViewById(R.id.recievedate);
        poddate = (EditText)findViewById(R.id.poddate);



        setupData();



        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);

        btnImg1 = (Button)findViewById(R.id.btnImg1);

        btnSubmit = (Button)findViewById(R.id.submit);
        btnCancel = (Button)findViewById(R.id.cancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                sendsave();

            }
        });



        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(CAMERA_REQUEST1);
            }
        });

        btnImg2 = (Button)findViewById(R.id.btnImg2);
        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(CAMERA_REQUEST2);
            }
        });

        btnImg3 = (Button)findViewById(R.id.btnImg3);
        btnImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(CAMERA_REQUEST3);
            }
        });
    }


    Bitmap bitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUEST1 && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            img1.setImageBitmap(bitmap);
           String filepath =  storeImage(bitmap);
           if (!filepath.equalsIgnoreCase("")){

               datax.setImg1(filepath);
               datax.save();
           }


        } else if (requestCode == CAMERA_REQUEST2 && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            img2.setImageBitmap(bitmap);
            String filepath =  storeImage(bitmap);
            if (!filepath.equalsIgnoreCase("")){
                datax.setImg2(filepath);
                datax.save();
            }

        }else if (requestCode == CAMERA_REQUEST3 && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            img3.setImageBitmap(bitmap);
            String filepath =  storeImage(bitmap);
            if (!filepath.equalsIgnoreCase("")){
                datax.setImg3(filepath);
                datax.save();
            }

        } else if (requestCode == Gallary_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            bitmap = BitmapFactory.decodeFile(picturePath);
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(picturePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotateImage(bitmap, 180);
                    break;
                // etc.
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);


                img1.setImageBitmap(bitmap);


        }

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

    public Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    void openCamera(int cameraRequest) {
        File directory = new File(
                Environment.getExternalStorageDirectory(), "epod" + "/" + getPackageName());
        try {
            SimpleDateFormat sdfPic = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currentDateandTime = sdfPic.format(new Date()).replace(" ", "");
            File imagesFolder = new File(directory.getAbsolutePath());
            imagesFolder.mkdirs();

            String fname = "IMAGE_" + currentDateandTime + ".jpg";
            File file = new File(imagesFolder, fname);
            fileUri = Uri.fromFile(file);
            Intent cameraIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(cameraIntent, cameraRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setupData(){

        String rawQuery = "SELECT  * FROM `Detailorder` where  driver ='"+AppController.username+"' and shipno ='"+shipNo+"' and orderno ='"+orderNo+"' order by shipno";
        StringQuery<Detailorder> stringQuery = new StringQuery<>(Detailorder.class, rawQuery);
        stringQuery
                .async()
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@android.support.annotation.NonNull Transaction transaction, @android.support.annotation.NonNull Throwable error) {
                        setupDataIfNull();
                    }
                })
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Detailorder>() {
                                             @Override
                                             public void onListQueryResult(QueryTransaction transaction, @NonNull List<Detailorder> models) {


                                                if (models.size()>0){
                                                    orderno.setText(models.get(0).getOrderno());
                                                    shipto.setText(models.get(0).getShipto());
                                                    shipname.setText(models.get(0).getShipname());
                                                    if (models.get(0).getRecieveddate().equalsIgnoreCase("")){
                                                        recievedate.setText(AppUtil.Now());
                                                        models.get(0).setRecieveddate(recievedate.getText().toString());
                                                    }else{
                                                        recievedate.setText( models.get(0).getRecieveddate());
                                                    }

                                                    if (models.get(0).getPoddate().equalsIgnoreCase("")){
                                                        poddate.setText(AppUtil.Now());
                                                        models.get(0).setPoddate(poddate.getText().toString());
                                                    }else{
                                                        poddate.setText( models.get(0).getPoddate());
                                                    }

                                                    if (models.get(0).getImg1()!=null){
                                                        Picasso.get().load(new File(models.get(0).getImg1())).into(img1);
                                                    }

                                                    if (models.get(0).getImg2()!=null){
                                                        Picasso.get().load(new File(models.get(0).getImg2())).into(img2);
                                                    }

                                                    if (models.get(0).getImg3()!=null){
                                                        Picasso.get().load(new File(models.get(0).getImg3())).into(img3);
                                                    }

                                                    datax = models.get(0);
                                                    datax.save();
                                                    setGPS();

                                                }else{
                                                    setupDataIfNull();
                                                }

                                             }


                                         }


                )
                .execute();

    }

    public void setupDataIfNull(){
        String rawQuery = "SELECT  * FROM `Dataorder` where driver ='"+ AppController.username+"' and shipmentNo ='"+shipNo+"' and orderNo ='"+orderNo+"' order by shipmentNo;";
        StringQuery<Dataorder> stringQuery = new StringQuery<>(Dataorder.class, rawQuery);
        stringQuery
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Dataorder>() {
                                             @Override
                                             public void onListQueryResult(QueryTransaction transaction, @NonNull List<Dataorder> models) {


                                                 orderno.setText(models.get(0).getOrderNo());
                                                 shipto.setText(models.get(0).getShipTo());
                                                 shipname.setText(models.get(0).getShipName());

                                                 recievedate.setText(AppUtil.Now());
                                                 poddate.setText(AppUtil.Now());

                                                 datax = new Detailorder();
                                                 datax.setId(models.get(0).getId());
                                                 datax.setOrderno(models.get(0).getOrderNo());
                                                 datax.setShipto(models.get(0).getShipTo());
                                                 datax.setShipname(models.get(0).getShipName());
                                                 datax.setRecieveddate(recievedate.getText().toString());
                                                 datax.setPoddate(poddate.getText().toString());
                                                 datax.setShipno(models.get(0).getShipmentNo());
                                                 datax.setDriver(AppController.username);
                                                 datax.save();
                                                 setGPS();




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
                    datax.setLocation(txtlocation.getText().toString());

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


    String TAG ="Save File Image";

    private String storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return "";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            return getOutputMediaFile().getAbsolutePath();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
            return "";
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
            return "";
        }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
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

    public void sendsave(){


        byte[] bImg1  = AppUtil.FiletoByteArray(new File(datax.getImg1()));
        byte[] bImg2  = AppUtil.FiletoByteArray(new File(datax.getImg2()));
        byte[] bImg3  = AppUtil.FiletoByteArray(new File(datax.getImg3()));


        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        //  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);

        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),bImg1);
        MultipartBody.Part bodyImg1 =
                MultipartBody.Part.createFormData("img1", datax.getOrderno()+NowX()+".jpg", requestFile1);

        RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"),bImg2);
        MultipartBody.Part bodyImg2 =
                MultipartBody.Part.createFormData("img2", datax.getOrderno()+NowX()+".jpg", requestFile2);

        RequestBody requestFile3 = RequestBody.create(MediaType.parse("image/jpeg"),bImg3);
        MultipartBody.Part bodyImg3 =
                MultipartBody.Part.createFormData("img3", datax.getOrderno()+NowX()+".jpg", requestFile3);



        progressDialog = new ProgressDialog(DetailOrderActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<UpdateOrder> updateService = apiInterface.updateData(
                toRequestBody(AppUtil.replaceNull(datax.getOrderno()).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getShipto() ).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getShipname() ).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getLocation() ).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getRecieveddate()).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getPoddate() ).trim()),
                bodyImg1,bodyImg2,bodyImg3,
                toRequestBody(AppUtil.replaceNull(datax.getStatus() ).trim()),
                toRequestBody(AppUtil.replaceNull(datax.getVerification() ).trim())



        );
        updateService.enqueue(new Callback<UpdateOrder>() {
            @Override
            public void onResponse(Call<UpdateOrder> call, Response<UpdateOrder> response) {
                progressDialog.dismiss();
                if (response != null) {
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.d("Test", response.message());

                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();

                            datax.setStatus("send");
                            finish();


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

}
