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

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tk.matheuslucena.realidade.Objetos.Cart;
import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.Objetos.OrderV2;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.adapter.OrderV2Adapter;
import tk.matheuslucena.realidade.adapter.ProductV3Adapter;
import tk.matheuslucena.realidade.dao.DAOCart;
import tk.matheuslucena.realidade.dao.DAOCliente;
import tk.matheuslucena.realidade.dao.DAOProduct;
import tk.matheuslucena.realidade.dao.OrderDAO;

public class CartV2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context = CartV2Activity.this;
    public List<OrderV2> dataListOrder = new ArrayList<OrderV2>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_v2);

        //Responsável em mostrar o menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Responsável em mostrar o menu


        Intent intent = getIntent();
        OrderV2 objOrder = (OrderV2) Objects.requireNonNull(intent.getExtras()).get("order");

        int sessionId = objOrder.getSessionId();
        int idProd = objOrder.product.getIdProduct();
        int quant = objOrder.getQuantity();

        assert objOrder != null;
        Toast.makeText(context,objOrder.product.getName() + " > "  + idProd + " > " + quant,Toast.LENGTH_LONG).show();

        populateList(sessionId, idProd, quant);

        ListView listView;
        listView = findViewById(R.id.list_viewCartV2);
        OrderV2Adapter adapter= new OrderV2Adapter(CartV2Activity.this,dataListOrder);
        listView.setAdapter(adapter);





    }


    public void populateList(int sessionId, int idProd, int quant) {
        OrderV2 objOrder = new OrderV2();
        objOrder.setIdProduct(idProd);
        objOrder.setSectionClient(sessionId);
        objOrder.setQuantity(quant);
        OrderDAO objOrderDAO = new OrderDAO(CartV2Activity.this);
        String result;

        if (idProd!=0 && quant!=0){
            result = objOrderDAO.InsertObject(objOrder);
        }

        List<OrderV2> listOrder  = new ArrayList<OrderV2>();
        String ord;
        ord = "Order by id";

        listOrder = objOrderDAO.SelectObject(ord);

        List<OrderV2> listOrderSession  = new ArrayList<OrderV2>();
        listOrderSession = OrderListSession(sessionId, listOrder);
        dataListOrder = insertDataListOrder(listOrderSession);

    }



    public List<OrderV2> OrderListSession(int sessionId, List<OrderV2> listOrder) {

        int x;
        List<OrderV2> listOrderSession  = new ArrayList<OrderV2>();
        OrderV2 objOrder = new OrderV2();

        for (x=0;x<listOrder.size();x++){
            objOrder = listOrder.get(x);

            if (objOrder.getSectionClient()==sessionId){
                listOrderSession.add(objOrder);
            }
        }
        return listOrderSession;
    }




    public List<OrderV2> insertDataListOrder(List<OrderV2> listOrder) {
        int x;
        DAOProduct objProdDao = new DAOProduct();
        List<OrderV2> listOrderProduct  = new ArrayList<OrderV2>();
        OrderV2 objOrder = new OrderV2();
        Product objProduct = new Product();

        for (x=0;x<listOrder.size();x++){
            objOrder = listOrder.get(x);
            objProduct = objProdDao.SelectSpecificIdObject(objProduct, objOrder);
            objOrder.setName(objProduct.getName());
            objOrder.setPrice(objProduct.getPrice());
            listOrderProduct.add(objOrder);
        }
        return listOrderProduct;
    }





/* This code is working but have log to show several part in console log
    public void populateList(int sessionId, int idProd, int quant) {
        Log.d("Populate", " variable > " + sessionId + " " + idProd + " " + quant);

        OrderV2 objOrder = new OrderV2();
        objOrder.setIdProduct(idProd);
        objOrder.setSectionClient(sessionId);
        objOrder.setQuantity(quant);
        OrderDAO objOrderDAO = new OrderDAO(CartV2Activity.this);
        String result = "";

        if (idProd!=0 && quant!=0){
            result = objOrderDAO.InsertObject(objOrder);
        }
        Log.d("Populate", " insert Cart > " + result);


        List<OrderV2> listOrder  = new ArrayList<OrderV2>();
        OrderV2 cart = new OrderV2();
        String ord;
        ord = "Order by id";
        //listOrder = objOrderDAO.GetProducts();
        listOrder = objOrderDAO.SelectObject(ord);

        for (int x=0;x<listOrder.size();x++) {
            cart = listOrder.get(x);
            Log.d("Populate 2", " select name > " + cart.getName() + " " + cart.getIdProduct());
        }

        List<OrderV2> listOrderSession  = new ArrayList<OrderV2>();
        listOrderSession = OrderListSession(sessionId, listOrder);
        dataListOrder = insertDataListOrder(listOrderSession);

        OrderV2 orderV2 = new OrderV2();
        for (int x=0;x<dataListOrder.size();x++) {
            orderV2 = listOrder.get(x);
            Log.d("Populate 3", " select name > " + orderV2.getName() + " " + orderV2.getIdProduct() + " " + orderV2.getPrice()) ;
        }
    }



    public List<OrderV2> OrderListSession(int sessionId, List<OrderV2> listOrder) {
        Log.d("OrderListSession ", " size 2 > " + listOrder.size());
        int x;
        List<OrderV2> listOrderSession  = new ArrayList<OrderV2>();
        OrderV2 objOrder = new OrderV2();

        for (x=0;x<listOrder.size();x++){
            objOrder = listOrder.get(x);

            if (objOrder.getSectionClient()==sessionId){
                Log.d("OrderListSession ", " cond > " + sessionId);
                listOrderSession.add(objOrder);
            }
        }
        Log.d("OrderListSession ", " size 3 > " + listOrderSession.size());
        return listOrderSession;
    }




    public List<OrderV2> insertDataListOrder(List<OrderV2> listOrder) {
        Log.d("insertDataListOrder ", " size 1 > " + listOrder.size());
        int x;
        DAOProduct objProdDao = new DAOProduct();
        List<OrderV2> listOrderProduct  = new ArrayList<OrderV2>();
        OrderV2 objOrder = new OrderV2();
        Product objProduct = new Product();

        for (x=0;x<listOrder.size();x++){
            objOrder = listOrder.get(x);
            objProduct = objProdDao.SelectSpecificIdObject(objProduct, objOrder);
            objOrder.setName(objProduct.getName());
            objOrder.setPrice(objProduct.getPrice());
            listOrderProduct.add(objOrder);
        }
        Log.d("insertDataListOrder ", " size 2 > " + listOrderProduct.size());
        return listOrderProduct;
    }

*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id != R.id.nav_cartV2)finish();
        switch (id){
            case R.id.nav_produtos:
                Intent products = new Intent(CartV2Activity.this, MainActivity.class); // essa é activity Inicial do app
                startActivity(products);
                break;
            case R.id.nav_cart:
                Intent cart = new Intent(CartV2Activity.this, CartActivity.class); // essa é activity Inicial do app
                startActivity(cart);
                break;

            case R.id.nav_cartV2:
                Intent order = new Intent(CartV2Activity.this, OrdersActivity.class); // essa é activity Inicial do app
                startActivity(order);
                break;

            case R.id.nav_sair:
                DAOCliente dao = new DAOCliente(getApplicationContext());
                dao.Logout();
                Intent intent = new Intent(CartV2Activity.this, LoginActivity.class); // essa é activity Inicial do app
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
