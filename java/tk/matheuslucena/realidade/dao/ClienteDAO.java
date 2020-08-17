package tk.matheuslucena.realidade.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Database.Database;
import tk.matheuslucena.realidade.Objetos.Client;
import tk.matheuslucena.realidade.Objetos.OrderV2;

public class ClienteDAO{
    private Database database;
    private SQLiteDatabase db;
    Context context;


    public ClienteDAO(Context context){
        database = new Database(context);
        this.context = context;
    }

    public String inserir(Client client){
        ContentValues valores;
        long resultado;

        db = database.getWritableDatabase();
        valores = new ContentValues();
        valores.put("idClient",client.getId());
        valores.put("Name",client.getName());
        valores.put("Email",client.getEmail());
        valores.put("Password",client.getPassword());

        resultado = db.insert("client", null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public void Logout(){
        ContentValues valores;
        db = database.getWritableDatabase();
        valores = new ContentValues();
        valores.put("Name", (String) null);
        valores.put("Email", (String) null);
        valores.put("Password", (String) null);

        db.insert("client", null, valores);
        db.close();
    }
    public Client getlasid() {
        db = database.getReadableDatabase();
        String q = "SELECT MAX(id_local),* FROM client";
        Cursor mCursor = db.rawQuery(q, null);

        Client cliente = new Client();
        mCursor.moveToFirst();

        int id = mCursor.getInt(mCursor.getColumnIndex("MAX(id_local)"));
        String name = mCursor.getString(mCursor.getColumnIndex("Name"));
        String email = mCursor.getString(mCursor.getColumnIndex("Email"));
        String password = mCursor.getString(mCursor.getColumnIndex("Password"));

        cliente.setId(id);
        cliente.setName(name);
        cliente.setEmail(email);
        cliente.setPassword(password);
        db.close();
        return cliente;
    }

    public Client SelectSpecificIdObject(Client objClient, OrderV2 objOrder) {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        final List<OrderV2> p_cart = new ArrayList<OrderV2>();
        Client objCl = new Client();
        try {
            String all_ids = "SELECT * FROM cart";
            Cursor mCursor = db.rawQuery(all_ids, null);
            mCursor.moveToFirst();
            while (true) {
                Log.d("GetProduct -> ", "get > " + mCursor.getColumnIndex("id"));
                OrderV2 cart = new OrderV2();
                cart.setQuantity(mCursor.getInt(mCursor.getColumnIndex("quanty")));
                cart.setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
                cart.setOrdered(mCursor.getInt(mCursor.getColumnIndex("ordered")));
                p_cart.add(cart);
                if(mCursor.isLast())break;
                mCursor.moveToNext();
            }
            mCursor.close();
            mCursor = null;
            int cont = 0;
            for (final OrderV2 p : p_cart) {
                String select_products = "SELECT * FROM product where id=" + p.getIdProduct();
                mCursor = db.rawQuery(select_products, null);
                mCursor.moveToFirst();

                final OrderV2 pdt = new OrderV2();
                p_cart.get(cont).setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
                p_cart.get(cont).setName(mCursor.getString(mCursor.getColumnIndex("name")));
                p_cart.get(cont).setPrice(mCursor.getFloat(mCursor.getColumnIndex("price")));
                p_cart.get(cont).setPicture1(mCursor.getString(mCursor.getColumnIndex("picture")));
                p_cart.get(cont).setQuantity(p.getQuantity());
                cont++;
            }

            assert mCursor != null;
            mCursor.close();

        } catch (Exception e) {
            Log.d("ERROR ", String.valueOf(e));
        } finally {
            db.close();
        }
        return objCl;
    }

}