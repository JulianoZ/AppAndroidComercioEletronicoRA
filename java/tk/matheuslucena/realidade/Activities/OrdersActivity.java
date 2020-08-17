package tk.matheuslucena.realidade.Activities;

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

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.RestInterface;
import tk.matheuslucena.realidade.adapter.OrderAdapter;
import tk.matheuslucena.realidade.dao.DAOCliente;

import static tk.matheuslucena.realidade.Activities.MainActivity.EndPointWsRest;

public class OrdersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    List<String> list_order = new ArrayList<>();
    boolean t_atualizacao = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        listView = findViewById(R.id.list_view);

        AtualizarPedidos();
    }

    protected void AtualizarPedidos() {
        Log.e("Util", "Start: " + Calendar.getInstance().getTime().toString());
        //final ProgressDialog loading = ProgressDialog.show(LoadActivity.this, "Carregando Dados", "Por favor, aguarde...", false, false);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(EndPointWsRest).build();
        final RestInterface orderApi = restAdapter.create(RestInterface.class);

        orderApi.getWRestPedidos(new Callback<List<Order>>() {
            @Override
            public void success(final List<Order> orders, Response response) {
                OrderAdapter adapter= new OrderAdapter(OrdersActivity.this,orders);
                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
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

        if(id != R.id.nav_orders){finish();t_atualizacao = false;}
        switch (id){
            case R.id.nav_cart:
                Intent cart = new Intent(OrdersActivity.this, CartActivity.class); // essa é activity Inicial do app
                startActivity(cart);
                break;
            case R.id.nav_prod_simples:
                Intent prod = new Intent(OrdersActivity.this, ProductSimpleActivity.class); // essa é activity Inicial do app
                startActivity(prod);
                break;

            case R.id.nav_gps:
                Intent gps = new Intent(OrdersActivity.this, GPSActivity.class); // essa é activity Inicial do app
                startActivity(gps);
                break;

            case R.id.nav_orders:
                Intent orders = new Intent(OrdersActivity.this, OrdersActivity.class); // essa é activity Inicial do app
                startActivity(orders);
                break;

            case R.id.nav_productV2:
                Intent act_prodV2 = new Intent(OrdersActivity.this, ProductV2Activity.class); // essa é activity Inicial do app
                startActivity(act_prodV2);
                break;


            case R.id.nav_cartV2:
                Intent act_cartV2 = new Intent(OrdersActivity.this, CartV2Activity.class); // essa é activity Inicial do app
                startActivity(act_cartV2);
                break;

            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent intent = new Intent(OrdersActivity.this, LoginActivity.class); // essa é activity Inicial do app
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
