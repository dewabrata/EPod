package epod.com.main.activity;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.rxgps.RxGps;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import epod.com.main.R;
import epod.com.main.application.AppController;
import epod.com.main.datamodel.ModelOrder.Dataorder;
import epod.com.main.utils.AppUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static epod.com.main.application.AppController.MY_PERMISSIONS_REQUEST_CAMERA;

public class DetailOrderActivity extends AppCompatActivity {

    public Uri fileUri;
    private int CAMERA_REQUEST1 = 11;
    private int CAMERA_REQUEST2 = 22;
    private int CAMERA_REQUEST3 = 33;

    private int Gallary_REQUEST = 101;

    private Button btnImg1 , btnImg2, btnImg3;
    private ImageView img1, img2, img3;
    private EditText orderno,shipto,shipname,txtlocation,recievedate,poddate;

    private String shipNo, orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);


        shipNo = getIntent().getStringExtra("shipno");
        orderNo = getIntent().getStringExtra("orderno");


        orderno = (EditText)findViewById(R.id.orderno);
        shipto = (EditText)findViewById(R.id.shipto);
        shipname = (EditText)findViewById(R.id.shipname);
        txtlocation = (EditText)findViewById(R.id.location);
        recievedate = (EditText)findViewById(R.id.recievedate);
        poddate = (EditText)findViewById(R.id.poddate);


        setGPS();
        setupData();



        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);

        btnImg1 = (Button)findViewById(R.id.btnImg1);
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            img1.setImageBitmap(bitmap);

        } else if (requestCode == CAMERA_REQUEST2 && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            img2.setImageBitmap(bitmap);

        }else if (requestCode == CAMERA_REQUEST3 && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            img3.setImageBitmap(bitmap);


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


}
