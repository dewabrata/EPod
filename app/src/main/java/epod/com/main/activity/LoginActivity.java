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
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;


import epod.com.main.R;
import epod.com.main.application.AppController;
import epod.com.main.datamodel.MobileUser.MobileUser;

import epod.com.main.service.APIClient;
import epod.com.main.service.APIInterfacesRest;
import epod.com.main.utils.AppUtil;
import epod.com.main.utils.SharedPreferencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static epod.com.main.application.AppController.MY_PERMISSIONS_REQUEST_CAMERA;

public class LoginActivity extends AppCompatActivity {

    APIInterfacesRest apiInterface;

    private SharedPreferencesUtil sharedPreferencesUtil;

    TextInputEditText mInputUsername,mInputPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesUtil = new SharedPreferencesUtil(this);
        Camerapermission();

        mInputUsername = (TextInputEditText) findViewById(R.id.username);
        mInputPassword = (TextInputEditText) findViewById(R.id.password);

        //debug

        //mInputUsername.setText("IWAN");
        //mInputPassword.setText("123456");




        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isEmptyEmail = AppUtil.isEmpty(mInputUsername);
                boolean isEmptyPassword = AppUtil.isEmpty(mInputPassword);
                if (isEmptyEmail) {
                    mInputUsername.setError("Enter your Username!");
                    mInputPassword.setError(null);
                } else if (isEmptyPassword) {
                    mInputPassword.setError("Enter your Password!");
                    mInputUsername.setError(null);
                } else {
                    mInputUsername.setError(null);
                    mInputPassword.setError(null);
                    final String username = mInputUsername.getText().toString().trim();
                    final String password = mInputPassword.getText().toString().trim();
                    callAuthentication(username, password);

                }

            }
        });







    }



    ProgressDialog progressDialog;
    public void callAuthentication(final String username, String password){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<MobileUser> call3 = apiInterface.getUser(username);
        call3.enqueue(new Callback<MobileUser>() {
            @Override
            public void onResponse(Call<MobileUser> call, Response<MobileUser> response) {
                progressDialog.dismiss();
                MobileUser userList = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (userList !=null) {

                    if ( userList.getData().getUserMobile().size()>0) {
                        String myUsername = userList.getData().getUserMobile().get(0).getUsername();
                        String myPass = userList.getData().getUserMobile().get(0).getPassword();
                        String myActive = userList.getData().getUserMobile().get(0).getIsactive();

                        if ((username.equalsIgnoreCase(username.toString().trim()) && (password.equalsIgnoreCase(password.toString().trim())))) {


                            sharedPreferencesUtil.setUsername(username);
                            AppController.setUsername(username);

                            startActivity(new Intent(LoginActivity.this, SearchDocument.class));
                        } else {

                            Toast.makeText(LoginActivity.this, "Username and Password doesnt match!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Username not found!", Toast.LENGTH_LONG).show();
                    }
                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MobileUser> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }


    private void Camerapermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LoginActivity.this
                ,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LoginActivity.this,
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

}
