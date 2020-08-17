package tk.matheuslucena.realidade.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.matheuslucena.realidade.Objetos.Cart;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.RestInterface;
import tk.matheuslucena.realidade.adapter.CartAdapter;
import tk.matheuslucena.realidade.dao.DAOCart;
import tk.matheuslucena.realidade.dao.DAOCliente;

import static java.lang.Thread.sleep;

public class   CartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int table = 0;
    List<Cart> list = new ArrayList<Cart>();
    ListView listView;
    CartAdapter adapterListView;
    LatLng local = null;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    TextView txt_quanty;
    public void AtualizarLista() {

        Parcelable state = listView.onSaveInstanceState();
        if (list.size() > 0)
            listView.setAdapter(adapterListView);
        else listView.setAdapter(null);
        listView.onRestoreInstanceState(state);
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkLocationPermission();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final DAOCart dao = new DAOCart(CartActivity.this);
        list = dao.GetProducts();
        txt_quanty = findViewById(R.id.txt_quanty);
        txt_quanty.setText(" " +String.valueOf(dao.CountProducts()));
        listView = (ListView) findViewById(R.id.list_view);
        adapterListView = new CartAdapter(CartActivity.this, list, txt_quanty);

        AtualizarLista();

        Button btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Cart p : list){
                    if(p.getOrdered() == 0){
                        dao.RemoveProduct(p);
                        list.remove(p);
                        txt_quanty.setText(" " +String.valueOf(dao.CountProducts()));
                        adapterListView.notifyDataSetChanged();
                    }
                }
                txt_quanty.setText(" " +String.valueOf(dao.CountProducts()));
            }
        });

        Button make_order = findViewById(R.id.btn_makeOrder);
        make_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!enabled) {
                    showSettingAlert();
                }
                if(checkLocationPermission()) {
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(CartActivity.this);
                    mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(CartActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.

                                if (location != null) {
                                    Location home = new Location("");
                                    home.setLatitude(-22.693467);
                                    home.setLongitude(-47.625520);
                                    if(location.distanceTo(home) <= 100000) {
                                        Log.d("Distancia ok", "OK");
                                        if (table == 0) { //If the number of client table isn´t set call the method to set.
                                            makeOrderSetTable(v).show(); //keep the products in the cart
                                        } else {
                                            makeOrderDirect(v).show();
                                        }
                                    }else{
                                        Toast.makeText(CartActivity.this,"Você está a mais de 500 metros do restaurante !",Toast.LENGTH_LONG).show();
                                    }
                                    AtualizarLista();
                                }else{Log.d("LOCATION", "NULL");}
                            }
                        });
                }
            }
        });

        Button btn_end = findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (table == 0) { //If the number of client table isn´t set call the method to set.
                    CheckoutTable(v).show();
                }else{
                    CheckoutDirect(v).show();
                }
                AtualizarLista();
                txt_quanty.setText(" " +String.valueOf(dao.CountProducts()));
            }
        });
    }
    public void showSettingAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS não está ativo, deseja ativar? (É necessario para realizar o pedido)  ");
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                CartActivity.this.startActivity(intent);
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
                                ActivityCompat.requestPermissions(CartActivity.this,
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
    private AlertDialog CheckoutTable(final View v) {
        LayoutInflater inflater = (LayoutInflater)
                v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NumberPicker npView = new NumberPicker(v.getContext());
        npView.setMinValue(1);
        npView.setMaxValue(20);
        return new AlertDialog.Builder(v.getContext())
                .setTitle("Selecione a sua mesa no restaurante")
                .setView(npView)
                .setPositiveButton("Fechar conta?",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Controller.vibrateShort(getView().getContext());
                                DAOCart dao = new DAOCart(CartActivity.this);
                                list = dao.GetProducts();
                                send_json(npView.getValue(),true); //Get the client table | Checkout indicates if the count to close or not
                                dao.RemoveAll();
                                list = dao.GetProducts();
                                listView.setAdapter(null);
                                AtualizarLista();
                                //Controller.vibrateShort(getView().getContext());
                                Toast.makeText(v.getContext(), "Conta fechada, aguarde o garçon...!", Toast.LENGTH_SHORT).show();
                            }

                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
    }



    private AlertDialog CheckoutDirect(final View v) {
        LayoutInflater inflater = (LayoutInflater)
                v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new AlertDialog.Builder(v.getContext())
                .setTitle("Deseja fechar a conta para a mesa:" + table + "?")
                .setPositiveButton("Fechar Conta",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                boolean checkout = true; //no checkout the odered
                                //Controller.vibrateShort(getView().getContext());
                                DAOCart dao = new DAOCart(CartActivity.this);
                                list = dao.GetProducts();
                                send_json(table, true); //Get the client table | Checkout indicates if the count to close or not
                                dao.RemoveAll();
                                list = dao.GetProducts();
                                listView.setAdapter(null);
                                AtualizarLista();
                                //Controller.vibrateShort(getView().getContext());
                                Toast.makeText(v.getContext(), "Conta fechada, aguarde o garçon...!", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
    }
    private AlertDialog makeOrderSetTable(final View v) { //Accumulates the products in the customer order
        LayoutInflater inflater = (LayoutInflater)
                v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NumberPicker npView = new NumberPicker(v.getContext());
        npView.setMinValue(1);
        npView.setMaxValue(20);
        return new AlertDialog.Builder(v.getContext())
                .setTitle("Selecione a sua mesa no bar")
                .setView(npView)
                .setPositiveButton("Fazer pedido",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Controller.vibrateShort(getView().getContext());
                                table = npView.getValue();

                                DAOCart dao = new DAOCart(CartActivity.this);
                                list = dao.GetProducts();
                                send_json(table,false); //Get the client table | Checkout indicates if the count to close or not
                                //Controller.Carrinho.clear();

                                //Controller.vibrateShort(getView().getContext());
                                Toast.makeText(v.getContext(), "Pedido realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
    }
    private void send_json(int ClientTable, final boolean checkout){ //ClientTable: Get client table
        //RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://www.pldlivros.com.br/").build();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://julianoblanco-001-site3.ctempurl.com/").build();
        RestInterface api = adapter.create(RestInterface.class);
        api.insertUserJson(generate_json(ClientTable,checkout ,list),
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader reader = null;
                        String output = "";
                        try{
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                            output = reader.readLine();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(!checkout) {
                            DAOCart dao = new DAOCart(CartActivity.this);
                            dao.UpdateOrdered(1);
                            list = dao.GetProducts();
                            AtualizarLista();
                        }else{
                            DAOCliente daocliente = new DAOCliente(CartActivity.this);
                            daocliente.ChangeKey();
                            DAOCart daoCart = new DAOCart(CartActivity.this);
                            daoCart.RemoveAll();
                            list = daoCart.GetProducts();
                            AtualizarLista();
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                    }
                }
        );
    }

    private String generate_json(int ClientTable,boolean checkout, List<Cart> list){
        try{
            DAOCliente dao = new DAOCliente(CartActivity.this);
            JSONObject cart = new JSONObject();
            cart.put("idFinalized", 0);
            cart.put("Finalized",false);
            cart.put("ClientId", dao.getlasid().getId()); //Cod Client Test
            if (checkout) { //If the count is close, send to the server this information
                cart.put("StatusOrdered", 1); //Delivery, Produce, Delivery...
            }else{
                cart.put("StatusOrdered", 0); //Delivery, Produce, Delivery...
            }
            cart.put("StatusOrderedLocal", 1); //Inside or Outside of store
            cart.put("Note", ""); //Note about the ordered
            cart.put("ZipCodeDelivery", 1); //ZipCode Sample to delivery
            cart.put("PayamentId", 1); //1 - Money, 2 - check or 3 - credit card
            cart.put("ValueChange", 0); //Change Value;
            cart.put("ClientTable", ClientTable); //Number of ClientTable
            cart.put("Checkout", Boolean.toString(checkout)); //Verify if the count is closed or not
            cart.put("PrimaryKey", dao.GetPrimaryKey());
            Log.i("Checkout", Boolean.toString(false));
            JSONArray products = new JSONArray();
            int total_quanty = 0;
            float total_value = 0;
            for (Cart prod:list){
                JSONObject p = new JSONObject();
                boolean ProductDelivered = false;
                Log.d("ORDERED ", String.valueOf(prod.getOrdered()));
                if(!checkout) {
                    if (prod.getOrdered() == 0) { //if the product hasn´t yet been purchased
                        total_quanty += prod.GetQuanty();
                        total_value += (prod.GetQuanty() * prod.getPrice());
                        p.put("quantity", prod.GetQuanty());
                        p.put("product_id", prod.getIdProduct());
                        p.put("ProductDelivered: ", ProductDelivered); //Used to staff know if the product was delivered to the table of client
                        products.put(p);
                    }
                }else{
                    total_quanty += prod.GetQuanty();
                    total_value += (prod.GetQuanty() * prod.getPrice());
                    p.put("quantity", prod.GetQuanty());
                    p.put("product_id", prod.getIdProduct());
                    p.put("ProductDelivered: ", ProductDelivered); //Used to staff know if the product was delivered to the table of client
                    products.put(p);
                }
                prod.setProduct_purchased(true); //set that the product already was ordered
            }
            cart.put("GeneralQuantity", total_quanty);
            cart.put("GeneralValueTotal", total_value);
            cart.put("products", products);
            if(total_quanty > 0)
                return cart.toString();
            else return "";
        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private AlertDialog makeOrderDirect(final View v) { //Accumulates the products in the customer order
        LayoutInflater inflater = (LayoutInflater)
                v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return new AlertDialog.Builder(v.getContext())
                .setTitle("Deseja realizar esse pedido para a mesa:" + table + "?")
                .setPositiveButton("Fazer pedido",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DAOCart dao = new DAOCart(CartActivity.this);
                                list = dao.GetProducts();
                                send_json(table,false); //Get the client table. Checkout indicates if the count to close or not
                                Toast.makeText(v.getContext(), "Pedido realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
    }
    public static Bitmap getBitmap(final Product p, final Context c, String url){
        Log.d("Load Image", "Loading from: " + url);
        Bitmap b;
        try{
            b = Picasso.with(c).load(url).get();
        }catch(IOException e){
            Log.d("Load Image", "Failed load from: " + url);
            b=null;
        }
        return b;
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
        if(id != R.id.nav_cart)finish();
        switch (id){
            case R.id.nav_produtos:

                Intent main = new Intent(CartActivity.this, MainActivity.class); // essa é activity Inicial do app
                startActivity(main);
                break;
            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent login = new Intent(CartActivity.this, LoginActivity.class); // essa é activity Inicial do app
                startActivity(login);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
