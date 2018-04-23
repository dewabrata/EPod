package epod.com.main.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.JSONObject;

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

public class DeliveryDocActivity extends AppCompatActivity {

    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterListBasicDelDoc mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_doc);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delivery Document");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

   /*     List<Dataorder> items = DataGenerator.getPeopleData(this);
        items.addAll(DataGenerator.getPeopleData(this));
        items.addAll(DataGenerator.getPeopleData(this));*/

        //set data and list adapter

        getDataOrder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
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


                   sqlQueryList();






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
                        sqlQueryList();
                    }
                }).build().execute();


    }



    public void sqlQueryList(){

        String rawQuery = "SELECT distinct * FROM `Dataorder` where driver ='"+AppController.username+"' group by shipmentNo;";
        StringQuery<Dataorder> stringQuery = new StringQuery<>(Dataorder.class, rawQuery);
        stringQuery
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Dataorder>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Dataorder> models) {

                        setupAdapterList(models);


                    }
                }


                )
                .execute();



  /*      ArrayList<Dataorder> data = (ArrayList)SQLite.select().from(Dataorder.class)
                .queryList();


        setupAdapterList(data);
*/
    }

    public void setupAdapterList(List<Dataorder>items){


             mAdapter = new AdapterListBasicDelDoc(this, items);
             recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasicDelDoc.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Dataorder obj, int position) {
                //Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),ShipToActivity.class);
                intent.putExtra("shipmentno",obj.getShipmentNo());
                startActivityForResult(intent,999);
            }
        });


    }



}
