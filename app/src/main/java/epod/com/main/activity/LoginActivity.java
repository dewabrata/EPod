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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import epod.com.main.R;
import epod.com.main.application.AppController;
import epod.com.main.datamodel.ModelLogin.Authentication;
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

        mInputUsername = (TextInputEditText) findViewById(R.id.username);
        mInputPassword = (TextInputEditText) findViewById(R.id.password);

        //debug

        mInputUsername.setText("IWAN");
        mInputPassword.setText("123456");



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
        Call<Authentication> call3 = apiInterface.getAuthentication(username,password);
        call3.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, Response<Authentication> response) {
                progressDialog.dismiss();
                Authentication userList = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (userList !=null) {
                    sharedPreferencesUtil.setUsername(username);
                    AppController.setUsername(username);

                     startActivity(new Intent(LoginActivity.this, DeliveryDocActivity.class));
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
            public void onFailure(Call<Authentication> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }



}
