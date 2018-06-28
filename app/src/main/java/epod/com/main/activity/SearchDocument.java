package epod.com.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import epod.com.main.R;
import epod.com.main.application.AppController;

import epod.com.main.datamodel.NewOrder.Dataorder;
import epod.com.main.datamodel.NewOrder.ModelOrder;
import epod.com.main.service.APIClient;
import epod.com.main.service.APIInterfacesRest;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchDocument extends AppCompatActivity {

    private ProgressBar progress_bar;
    private FloatingActionButton fab,fab2;
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

        TextView txtHelo = (TextView)findViewById(R.id.txtUsername);
        txtHelo.setText("User : "+AppController.getUsername());





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 111 && resultCode==222 ){
            if (data!=null){

                et_search.post(new Runnable() {    // Use the post method of the TextView
                    public void run() {
                        et_search.setText(    // Update the TextView
                               data.getStringExtra("barcode")
                        );

                    }
                });

            }
        }
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
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
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
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(SearchDocument.this, QRActivity.class);
                startActivityForResult(intent,111);
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
