package tk.matheuslucena.realidade.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.matheuslucena.realidade.Objetos.Client;
import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.RestInterface;
import tk.matheuslucena.realidade.SendName;
import tk.matheuslucena.realidade.TCPClient;
import tk.matheuslucena.realidade.adapter.ProductAdapter;
import tk.matheuslucena.realidade.dao.DAOCart;
import tk.matheuslucena.realidade.dao.DAOCliente;
import tk.matheuslucena.realidade.dao.DAOProduct;

import static java.security.AccessController.getContext;


/*
*
* fazer uma thread que atualiza os produtos
* verificar se houve alteração entre o sqlite e na nuvem, caso houver atualizar
* atualizar o banco de dados local para ver novos produtos
* atualizar ui, deletando, atualizando ou inserindo novos produtos.
* */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DataClient.OnDataChangedListener{

    public static Client client;
    DrawerLayout drawer;
    NavigationView navigationView;
    ProductAdapter adapterListView;
    public static final String EndPointWsRest = "http://julianoblanco-001-site3.ctempurl.com";
    boolean t_atualizacao = true;
    private static final String KEY = "com.example.key.selectproduct";
    public static List<Product> ProductList = new ArrayList<Product>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        AtualizarProdutos();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DAOProduct dao = new DAOProduct(MainActivity.this);
        ProductList = dao.getProducts();
        listView = findViewById(R.id.list_view);
        //adapterListView.notifyDataSetChanged();
        //Parcelable state = listView.onSaveInstanceState();
        TextView txt_quanty = findViewById(R.id.txt_quanty);

        DAOCart daoCart = new DAOCart(MainActivity.this);
        txt_quanty.setText(String.valueOf(daoCart.CountProducts()));

        adapterListView = new ProductAdapter(MainActivity.this, ProductList,txt_quanty );
        listView.setAdapter(adapterListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TCPClient.send_recv(adapterListView.getItem(position).getName());
                }
            }).start();
            }
        });
        //listView.onRestoreInstanceState(state);
        //listView.setCacheColorHint(Color.TRANSPARENT);

        new SendName("Aplicação Wear", MainActivity.this).execute();

        Switch ar = findViewById(R.id.switch1);
        ar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ProductList = null;
                if(isChecked)ProductList = dao.getAR();
                else ProductList = dao.getProducts();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    adapterListView.ChangeList(ProductList);
                    adapterListView.notifyDataSetChanged();
                    listView.deferNotifyDataSetChanged();
                    }
                });
            }
        });
        final DAOCliente daoclient = new DAOCliente(getApplicationContext());
        client = daoclient.getlasid();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView email = (TextView) headerView.findViewById(R.id.edt_email);
        TextView nome = (TextView) headerView.findViewById(R.id.txt_nome);

        email.setText(client.getEmail());
        nome.setText(client.getName());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ImageView imageView = headerView.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modify = new Intent(MainActivity.this,ModifyUser.class);
                startActivity(modify);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        new SendName("Aplicação Wear", MainActivity.this).execute();
                        AtualizarProdutos();
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        final TextView status = (TextView)findViewById(R.id.conexao);
                        if(TCPClient.send_recv("handshake").equals("hi")) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    status.setText("Conectado");
                                }
                            });
                        }else{
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    status.setText("Desconectado");
                                }
                            });
                        }
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */
    }

    protected void AtualizarProdutos() {
        Log.e("Util", "Start: " + Calendar.getInstance().getTime().toString());
        //final ProgressDialog loading = ProgressDialog.show(LoadActivity.this, "Carregando Dados", "Por favor, aguarde...", false, false);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(EndPointWsRest).build();
        final RestInterface productApi = restAdapter.create(RestInterface.class);

        productApi.getWSRestProduct(new Callback<List<Product>>() {
            @Override
            public void success(final List<Product> products, Response response) {
                final DAOProduct dao = new DAOProduct(MainActivity.this);
                dao.removeAllProducts();
                HandlerThread handlerThread = new HandlerThread("Loading Thread");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(Product p : products){
                            dao.inserir(p);
                        }

                    }
                });
                ProductList = products;
                String produtos = "";
                for(Product p : ProductList){
                    if(p.getAR().equals("true")){
                        produtos += p.getName() + ";";
                    }
                }
                if(produtos.length() > 1) {
                    produtos = produtos.substring(0, produtos.length() - 1);
                    new SendName(produtos,MainActivity.this).execute();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("FAIL","FAIL");
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }
    @Override
    public void onResume(){
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/count") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    String val = dataMap.getString(KEY);
                    for (final Product p : ProductList){
                        if(p.getName().equals(val)){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    TCPClient.send_recv(p.getName());
                                }
                            }).start();
                        }
                    }
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id != R.id.nav_produtos){finish();t_atualizacao = false;}
        switch (id){
            case R.id.nav_cart:
                Intent cart = new Intent(MainActivity.this, CartActivity.class); // essa é activity Inicial do app
                startActivity(cart);
                break;
            case R.id.nav_prod_simples:
                Intent prod = new Intent(MainActivity.this, ProductSimpleActivity.class); // essa é activity Inicial do app
                startActivity(prod);
                break;

            case R.id.nav_gps:
                Intent gps = new Intent(MainActivity.this, GPSV2Activity.class); // essa é activity Inicial do app
                startActivity(gps);
                break;

            case R.id.nav_orders:
                Intent orders = new Intent(MainActivity.this, OrdersActivity.class); // essa é activity Inicial do app
                //Intent orders = new Intent(MainActivity.this, OrderActivityTeste.class); // essa é activity Inicial do app
                //Order order = new Order();
                //orders.putExtra("orderFinalized", order);

                startActivity(orders);
                break;

            case R.id.nav_cartV2:
                Intent act_gps = new Intent(MainActivity.this, CartV2Activity.class); // essa é activity Inicial do app
                startActivity(act_gps);
                break;

            case R.id.nav_productV2:
                Intent act_prodctV2 = new Intent(MainActivity.this, ProductV2Activity.class); // essa é activity Inicial do app
                startActivity(act_prodctV2);
                break;


            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class); // essa é activity Inicial do app
                startActivity(intent);
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
