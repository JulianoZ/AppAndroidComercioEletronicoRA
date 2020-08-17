package tk.matheuslucena.realidade.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.dao.DAOCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class GPSActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean t_atualizacao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        //TextView textElement = (TextView) findViewById(R.id.getGPS);
        //textElement.setText("Puxando dados do GPS"); //leave this line to assign a specific text
        //textElement.setText(R.string.my_love_text); //leave this line to assign a string resource


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


/*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
*/

        //CartActivity objCart = new CartActivity();
        //objCart.checkLocationPermission();

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        */

        /*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */


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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id != R.id.nav_gps){finish();t_atualizacao = false;}
        switch (id){
            case R.id.nav_cart:
                Intent cart = new Intent(GPSActivity.this, CartActivity.class); // essa é activity Inicial do app
                startActivity(cart);
                break;
            case R.id.nav_prod_simples:
                Intent prod = new Intent(GPSActivity.this, ProductSimpleActivity.class); // essa é activity Inicial do app
                startActivity(prod);
                break;

            case R.id.nav_orders:
                Intent orders = new Intent(GPSActivity.this, OrdersActivity.class); // essa é activity Inicial do app
                startActivity(orders);
                break;

            case R.id.nav_cartV2:
                Intent act_gps = new Intent(GPSActivity.this, CartV2Activity.class); // essa é activity Inicial do app
                startActivity(act_gps);
                break;

            case R.id.nav_productV2:
                Intent act_prodctV2 = new Intent(GPSActivity.this, ProductV2Activity.class); // essa é activity Inicial do app
                startActivity(act_prodctV2);
                break;


            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent intent = new Intent(GPSActivity.this, LoginActivity.class); // essa é activity Inicial do app
                startActivity(intent);
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
