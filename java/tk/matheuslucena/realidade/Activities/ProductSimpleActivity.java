package tk.matheuslucena.realidade.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.TCPClient;
import tk.matheuslucena.realidade.adapter.ProductAdapter;
import tk.matheuslucena.realidade.dao.DAOCliente;
import tk.matheuslucena.realidade.dao.DAOProduct;

public class ProductSimpleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProductAdapter adapterListView;
    public static List<Product> ProductList = new ArrayList<Product>();
    ListView listView;
    boolean t_atualizacao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_simple);

        //Menu
        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */
        //Menu


        //Menu
/*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
*/
        //Menu



        //setContentView(R.layout.activity_main);

        //List<Product> ProductList = new ArrayList<Product>();
        //ProductAdapter adapterListView;
        //ListView listView;

        //final DAOProduct dao = new DAOProduct(ProductSimpleActivity.this);
        //ProductList = dao.getProducts();


        populateList();

        listView = findViewById(R.id.list_view);
        TextView txt_quanty = findViewById(R.id.txt_quanty);

        adapterListView = new ProductAdapter(ProductSimpleActivity.this, ProductList,txt_quanty );
        listView.setAdapter(adapterListView);


    }


    public void populateList() {
        DAOProduct objProd = new DAOProduct(ProductSimpleActivity.this);
        //DAOProduct objProd = new DAOProduct();
        //List<Product> dataList  = new ArrayList<Product>();
        String ord;
        ord = "Order by name ";
        ProductList = objProd.getProducts();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id != R.id.nav_prod_simples){finish();t_atualizacao = false;}
        switch (id){
            case R.id.nav_cart:
                Intent cart = new Intent(ProductSimpleActivity.this, CartActivity.class); // essa é activity Inicial do app
                startActivity(cart);
                break;
            case R.id.nav_produtos:
                Intent prod = new Intent(ProductSimpleActivity.this, Product.class); // essa é activity Inicial do app
                startActivity(prod);
                break;

            case R.id.nav_gps:
                Intent gps = new Intent(ProductSimpleActivity.this, GPSActivity.class); // essa é activity Inicial do app
                startActivity(gps);
                break;

            case R.id.nav_orders:
                Intent orders = new Intent(ProductSimpleActivity.this, OrdersActivity.class); // essa é activity Inicial do app
                startActivity(orders);
                break;

            case R.id.nav_cartV2:
                Intent act_gps = new Intent(ProductSimpleActivity.this, CartV2Activity.class); // essa é activity Inicial do app
                startActivity(act_gps);
                break;

            case R.id.nav_productV2:
                Intent act_prodctV2 = new Intent(ProductSimpleActivity.this, ProductV2Activity.class); // essa é activity Inicial do app
                startActivity(act_prodctV2);
                break;

            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent intent = new Intent(ProductSimpleActivity.this, LoginActivity.class); // essa é activity Inicial do app
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
