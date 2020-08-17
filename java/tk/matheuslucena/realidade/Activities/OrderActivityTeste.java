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
import java.util.Objects;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.matheuslucena.realidade.Objetos.Client;
import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.Objetos.OrderV2;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.RestInterface;
import tk.matheuslucena.realidade.adapter.OrderAdapter;
import tk.matheuslucena.realidade.adapter.OrderV2Adapter;
import tk.matheuslucena.realidade.dao.ClienteDAO;
import tk.matheuslucena.realidade.dao.DAOCliente;
import tk.matheuslucena.realidade.dao.OrderDAO;

import static tk.matheuslucena.realidade.Activities.MainActivity.EndPointWsRest;

public class OrderActivityTeste extends AppCompatActivity {

    //Testando o código gerado pela DSL

    public List<OrderV2> dataListOrderFinalized = new ArrayList<OrderV2>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Declaration of variable in case to get variable from another screen
        Intent intent = getIntent();
        Order objOrderFinalized = (Order) Objects.requireNonNull(intent.getExtras()).get("orderFinalized");
        int idOrderedFinalized = objOrderFinalized.getIdOrderedFinalized();
        int statusOrdered = objOrderFinalized.getStatusOrdered();

        populateList(idOrderedFinalized, statusOrdered);

        //Instantiate the adapter in case get variable from another screen
        ListView listView;
        listView = findViewById(R.id.list_view);
        OrderV2Adapter adapter = new OrderV2Adapter(OrderActivityTeste.this, dataListOrderFinalized);
        listView.setAdapter(adapter);


    }



    public void populateList(int idOrderedFinalized, int statusOrdered) {
        OrderDAO objOrderFinalizedDAO = new OrderDAO(OrderActivityTeste.this);
        List<OrderV2> listOrderFinalized  = new ArrayList<OrderV2>();
        String ord;
        ord = "Order by id desc";
        listOrderFinalized = objOrderFinalizedDAO.SelectObject(ord);
        adjustStatusOrder(idOrderedFinalized, statusOrdered, listOrderFinalized);
        //List<OrderV2> dataListOrderFinalized = new ArrayList<OrderV2>();
        dataListOrderFinalized = insertDataListOrder(listOrderFinalized);

    }


    public List<OrderV2> insertDataListOrder(List<OrderV2> listOrderFinalized) {
        int x;
        List<OrderV2> listOrderFin  = new ArrayList<OrderV2>();
        ClienteDAO objUserDao = new ClienteDAO(OrderActivityTeste.this);
        Client objUser = new Client();
        OrderV2 objOrderFinalized = new OrderV2();

        for (x=0;x<listOrderFinalized.size();x++){
            objOrderFinalized = listOrderFinalized.get(x);
            objUser = objUserDao.SelectSpecificIdObject(objUser, objOrderFinalized);
            objOrderFinalized.setClient(objUser);
            listOrderFin.add(objOrderFinalized);
        }

        return listOrderFin;
    }


    public void adjustStatusOrder(int idOrderedFinalized, int statusOrdered, List<OrderV2> listOrderFinalized) {

        if ((idOrderedFinalized!=0)){
            int x;
            OrderV2 objOF = new OrderV2();
            OrderDAO objFDao = new OrderDAO(OrderActivityTeste.this);

            for (x=0;x<listOrderFinalized.size();x++){
                objOF = listOrderFinalized.get(x);

                if (objOF.id==idOrderedFinalized){
                    int statusOr;
                    statusOr = adjustStatus(statusOrdered);
                    objOF.setStatusOrdered(statusOr);
                    //result = objFDao.UpdateObject(objOF);
                }
            }

        }
    }


    public Integer adjustStatus(int CurrentStatusOrder) {
        int status;
        status = 0;

        if ((CurrentStatusOrder==0)){
            status = 1;
        }
        else if((CurrentStatusOrder==1)){
            status = 2;
        }
        else if((CurrentStatusOrder==2)){
            status = 3;
        }
        else if((CurrentStatusOrder==3)){
            status = 4;
        }

        return status;
    }

}
