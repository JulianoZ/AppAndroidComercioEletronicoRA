package tk.matheuslucena.realidade.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.matheuslucena.realidade.Objetos.Cart;
import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.Objetos.OrderV2;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.RestInterface;
import tk.matheuslucena.realidade.adapter.OrderAdapter;
import tk.matheuslucena.realidade.adapter.ProductAdapter;

import tk.matheuslucena.realidade.adapter.ProductV3Adapter;
import tk.matheuslucena.realidade.dao.DAOCliente;
import tk.matheuslucena.realidade.dao.DAOProduct;

import static tk.matheuslucena.realidade.Activities.MainActivity.EndPointWsRest;

public class ProductV2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    List<Product> listProduct = null;
    ListView listView;
    ProductAdapter adapterListView;
    boolean t_atualizacao = true;
    Context context = this;


    List<String> list_order = new ArrayList<>(); //Orders


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_v2);

        //Menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Menu

        //Send data to Adapter
/*
        final DAOProduct dao = new DAOProduct(ProductV2Activity.this);
        ProductList = dao.getProducts();

        for(int i=0; i<ProductList.size(); i++) {
            Product currentX = ProductList.get(i);
            Toast.makeText(context,currentX.getName(),Toast.LENGTH_LONG).show();
            // Do something with the value
        }

        listView = findViewById(R.id.list_viewProductV2);

        ProductV2Adapter adapterListView = new ProductV2Adapter(ProductV2Activity.this, ProductList);
        listView.setAdapter(adapterListView);
*/



        /*
        List<Order> orders = new ArrayList<Order>();
        Order objOrder = new Order();
        objOrder.setName("Juliano");
        orders.add(objOrder);


        Order objOrder1 = new Order();
        objOrder1.setName("Sara");
        orders.add(objOrder1);


        ProductV3Adapter adapter= new ProductV3Adapter(ProductV2Activity.this,orders);
        listView.setAdapter(adapter);
        */

        //AtualizarPedidos();


        listView = findViewById(R.id.list_viewProductV2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listProduct != null){
                    /*
                    Intent intent = new Intent(ProductV2Activity.this,CartV2Activity.class);
                    intent.putExtra("order",listProduct.get(position));
                    startActivity(intent);
                    */

                    Intent intent = new Intent(ProductV2Activity.this,CartV2Activity.class);
                    OrderV2 objCart = new OrderV2();
                    objCart.setProduct(listProduct.get(position));
                    objCart.setSectionClient(0);
                    objCart.setQuantity(1);
                    intent.putExtra("order", objCart);
                    startActivity(intent);
                    /*
                    Cart objCart = new Cart();
                    objCart.setProduct(listProduct.get(position));
                    objCart.setSessionId(0);
                    objCart.SetQuanty(1);
                    intent.putExtra("order", objCart);
                    startActivity(intent);
                    */
                }
            }
        });


        //List<Product> listProduct = new ArrayList<Product>();
        final DAOProduct dao = new DAOProduct(ProductV2Activity.this);
        listProduct = dao.getProducts();


        /*
        List<Product> orders = new ArrayList<Product>();
        Product objOrder = new Product();
        objOrder.setName("Juliano");
        orders.add(objOrder);


        Product objOrder1 = new Product();
        objOrder1.setName("Sara");
        orders.add(objOrder1);
        */

        ProductV3Adapter adapter= new ProductV3Adapter(ProductV2Activity.this,listProduct);
        listView.setAdapter(adapter);



    }


/*
    protected void AtualizarPedidos() {
        Log.e("Util", "Start: " + Calendar.getInstance().getTime().toString());
        //final ProgressDialog loading = ProgressDialog.show(LoadActivity.this, "Carregando Dados", "Por favor, aguarde...", false, false);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(EndPointWsRest).build();
        final RestInterface orderApi = restAdapter.create(RestInterface.class);

        orderApi.getWRestPedidos(new Callback<List<Order>>() {
            @Override
            public void success(final List<Order> orders, Response response) {
                ProductV3Adapter adapter= new ProductV3Adapter(ProductV2Activity.this,orders);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id != R.id.nav_productV2){finish();t_atualizacao = false;}
        switch (id){
            case R.id.nav_produtos:
                Intent products = new Intent(ProductV2Activity.this, MainActivity.class); // essa é activity Inicial do app
                startActivity(products);
                break;

            case R.id.nav_cart:
                Intent cart = new Intent(ProductV2Activity.this, CartActivity.class); // essa é activity Inicial do app
                startActivity(cart);
                break;

            case R.id.nav_cartV2:
                Intent cartV2 = new Intent(ProductV2Activity.this, CartV2Activity.class); // essa é activity Inicial do app
                startActivity(cartV2);
                break;

            case R.id.nav_orders:
                Intent order = new Intent(ProductV2Activity.this, OrdersActivity.class); // essa é activity Inicial do app
                startActivity(order);
                break;


            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent intent = new Intent(ProductV2Activity.this, LoginActivity.class); // essa é activity Inicial do app
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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



}
