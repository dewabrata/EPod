package epod.com.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import epod.com.main.R;
import epod.com.main.adapter.AdapterListBasicDelDoc;
import epod.com.main.application.AppController;
import epod.com.main.datamodel.ModelOrder.Dataorder;
import epod.com.main.datamodel.ModelOrder.ModelOrder;
import epod.com.main.service.APIClient;
import epod.com.main.service.APIInterfacesRest;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchDocument extends AppCompatActivity {

    private ProgressBar progress_bar;
    private FloatingActionButton fab;
    private EditText et_search;
    private String shipNo, orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_primary_bg);
        initToolbar();
        initComponent();
        shipNo = getIntent().getStringExtra("shipno");
        orderNo = getIntent().getStringExtra("orderno");

        getDataOrder();

        final SurfaceView cameraView = (SurfaceView) findViewById(R.id.camera_view);

        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        final CameraSource cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());

                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }

        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    et_search.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            et_search.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });
                }
            }
        });
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        et_search = (EditText) findViewById(R.id.et_search);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN );
        fab = (FloatingActionButton) findViewById(R.id.fab);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    searchAction();
                    return true;
                }
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                searchAction();
            }
        });

    }

    private void searchAction() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(!et_search.getText().toString().equalsIgnoreCase("")){
                    progress_bar.setVisibility(View.VISIBLE);
                    fab.setAlpha(0f);
                    sqlQueryList(et_search.getText().toString());
                }else{
                    Toast.makeText(SearchDocument.this, "Must Input Document No !", Toast.LENGTH_SHORT).show();
                }

            }
        }, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    public List<Dataorder> orderItems;

    public void getDataOrder(){
        APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<ModelOrder> call3;

        call3 = apiInterface.getOrder(AppController.getUsername());

        call3.enqueue(new Callback<ModelOrder>() {
            @Override
            public void onResponse(Call<ModelOrder> call, Response<ModelOrder> response) {
                ModelOrder order = response.body();



                if(response.isSuccessful()) {
                    if (response.body()!=null) {
                        //  Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                        orderItems = (ArrayList<Dataorder>) order.getData().getDataorder();

                        savedb();

                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            ArrayList<Dataorder> orderLists;
            @Override
            public void onFailure(Call<ModelOrder> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Terjadi gangguan koneksi",Toast.LENGTH_LONG).show();
                call.cancel();









            }

        });

    }


    public void savedb(){

        FlowManager.getDatabase(AppController.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Dataorder>() {
                            @Override
                            public void processModel(Dataorder orderItem, DatabaseWrapper wrapper) {

                                orderItem.save();


                            }

                        }).addAll(orderItems).build())  // add elements (can also handle multiple)
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        Toast.makeText(getApplicationContext(),"Data Tersimpan",Toast.LENGTH_LONG).show();

                    }
                }).build().execute();


    }



    public void sqlQueryList(String orderno){

        String rawQuery = "SELECT distinct * FROM `Dataorder` where driver ='"+AppController.username+"' and shipmentNo ='"+orderno+"' group by shipmentNo;";
        StringQuery<Dataorder> stringQuery = new StringQuery<>(Dataorder.class, rawQuery);
        stringQuery
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Dataorder>() {
                                             @Override
                                             public void onListQueryResult(QueryTransaction transaction, @NonNull List<Dataorder> models) {

                                                 progress_bar.setVisibility(View.GONE);
                                                 fab.setAlpha(1f);
                                                 if (models.size()>0){

                                                     Intent intent = new Intent(getApplicationContext(),ShipToActivity.class);
                                                     intent.putExtra("shipmentno",models.get(0).getShipmentNo());
                                                     startActivityForResult(intent,999);

                                                 }else{
                                                     Toast.makeText(getApplicationContext(),"Document ID Not Found !",Toast.LENGTH_LONG).show();
                                                 }


                                             }
                                         }


                )
                .execute();



  /*      ArrayList<Dataorder> data = (ArrayList)SQLite.select().from(Dataorder.class)
                .queryList();


        setupAdapterList(data);
*/
    }










}
