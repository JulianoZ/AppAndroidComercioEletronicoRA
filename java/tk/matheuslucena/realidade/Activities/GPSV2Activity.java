package tk.matheuslucena.realidade.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.GPSMain;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.dao.DAOCliente;

public class GPSV2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean t_atualizacao = true;
    Context context = this;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private FusedLocationProviderClient mFusedLocationClient;

    List<String> dataRealLocationList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsv2);



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




        //Get GPS


        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            showSettingAlert();
        }





        if(checkLocationPermission()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(GPSV2Activity.this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(GPSV2Activity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            List<String> dataLocationList = new ArrayList<String>();
                            dataLocationList.add("Longitude");
                            dataLocationList.add("Latitude");


                            //GPSMain objGPSMain = new GPSMain();
                            //List<String> dataRealLocationList = objGPSMain.getDataLocation(location, dataLocationList);
                            //dataRealLocationList = objGPSMain.getDataLocation(location, dataLocationList);
                            dataRealLocationList = getDataLocation(location, dataLocationList);
                            TextView textElement = (TextView) findViewById(R.id.getGPS);
                            String data="";
                            for(int x=0;x<dataLocationList.size();x++) {
                                Log.d("GPS Test Activity", " variable > " + dataLocationList.get(x) + "  " + dataRealLocationList.get(x));
                                data += dataLocationList.get(x) + "  " + dataRealLocationList.get(x) + " | ";

                                //Log.d("GPS Test Activity", " variable > " + dataLocationList.get(x));
                            }
                            textElement.setText(data);

                            // Got last known location. In some rare situations this can be null.

                            //if (location != null) {
                           //   Toast.makeText(GPSV2Activity.this,"Dados: !" + location.getLatitude() + " " + location.getLongitude(),Toast.LENGTH_LONG).show();
                            //}else{Log.d("LOCATION", "NULL");}

                        }
                    });
        }



    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permissão GPS")
                        .setMessage("Por favor, ative seu gps.")
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(GPSV2Activity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    public void showSettingAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GPSV2Activity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS não está ativo, deseja ativar? (É necessario para realizar o pedido)  ");
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                GPSV2Activity.this.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }





    public List<String> getDataLocation(Location location, List<String> data){

        List<String> dataLocationList = new ArrayList<String>();
        String getData;
        for(int x=0;x<data.size();x++) {
            getData = "";
            if (location != null) {

                if(data.get(x) == "Latitude"){
                    getData = String.valueOf(location.getLatitude());
                    Log.d("GPS 1", " cond > " + data.get(x) + " " + getData + " " + x);
                }else if(data.get(x) == "Longitude") {
                    getData = String.valueOf(location.getLongitude());
                    Log.d("GPS 2", " cond > " + data.get(x) + " " + getData + " " + x);
                }
                dataLocationList.add(getData);
                Log.d("data location ", " data location > "+ dataLocationList.get(x));
            } else {
                Log.d("LOCATION", "NULL");
            }
        }
        return dataLocationList;
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id != R.id.nav_gps){finish();t_atualizacao = false;}
        switch (id){
            case R.id.nav_produtos:
                Intent products = new Intent(GPSV2Activity.this, MainActivity.class); // essa é activity Inicial do app
                startActivity(products);
                break;

            case R.id.nav_cart:
                Intent cart = new Intent(GPSV2Activity.this, CartActivity.class); // essa é activity Inicial do app
                startActivity(cart);
                break;

            case R.id.nav_cartV2:
                Intent cartV2 = new Intent(GPSV2Activity.this, CartV2Activity.class); // essa é activity Inicial do app
                startActivity(cartV2);
                break;

            case R.id.nav_orders:
                Intent order = new Intent(GPSV2Activity.this, OrdersActivity.class); // essa é activity Inicial do app
                startActivity(order);
                break;


            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent intent = new Intent(GPSV2Activity.this, LoginActivity.class); // essa é activity Inicial do app
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
