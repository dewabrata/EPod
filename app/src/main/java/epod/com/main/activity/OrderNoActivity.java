package epod.com.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.List;

import epod.com.main.R;
import epod.com.main.adapter.AdapterListBasicOrder;
import epod.com.main.adapter.AdapterListBasicShipTo;
import epod.com.main.application.AppController;
import epod.com.main.datamodel.ModelOrder.Dataorder;
import io.reactivex.annotations.NonNull;

public class OrderNoActivity extends AppCompatActivity {

    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterListBasicOrder mAdapter;
    private String shipmentNo;
    private String shipTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_doc);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();

        shipmentNo = getIntent().getStringExtra("shipno");
        shipTo = getIntent().getStringExtra("shipto");
        if (shipmentNo!=null && shipTo!=null){
            sqlQueryList();
        }


    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Number");
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




    public void sqlQueryList(){

        String rawQuery = "SELECT distinct * FROM `Dataorder` where driver ='"+AppController.username+"' and shipmentNo ='"+shipmentNo+"' and shipTo ='"+shipTo+"' group by orderno;";
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


             mAdapter = new AdapterListBasicOrder(this, items);
             recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasicOrder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Dataorder obj, int position) {


                Intent intent = new Intent(getApplicationContext(),DetailOrderActivity.class);
                intent.putExtra("shipno",obj.getShipmentNo());
                intent.putExtra("orderno",obj.getOrderNo());
                startActivityForResult(intent,999);

            }
        });


    }



}
