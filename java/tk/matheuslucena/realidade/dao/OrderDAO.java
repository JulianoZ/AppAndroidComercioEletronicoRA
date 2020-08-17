package tk.matheuslucena.realidade.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Database.Database;
import tk.matheuslucena.realidade.Objetos.Cart;
import tk.matheuslucena.realidade.Objetos.OrderV2;

public class OrderDAO {

    Context context;

    public OrderDAO(Context context){
        this.context = context;
    }


    public String InsertObject(OrderV2 objOrder){
        long resultado = 0;
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        Log.d("InsertObject", " ins 1 > " + objOrder.getIdProduct() + objOrder.getQuantity());
        try {
            ContentValues valores;
            valores = new ContentValues();
            valores.put("id",objOrder.getIdProduct());
            valores.put("quanty",objOrder.getQuantity());
            valores.put("ordered",0);
            resultado = db.insert("cart", null, valores);
        }catch (Exception e){

        }finally {
            db.close();
        }
        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }





    public List<OrderV2> SelectObject(String ord) {
            Database database = Database.getInstance(context);
            SQLiteDatabase db = database.getWritableDatabase();
            final List<OrderV2> p_cart = new ArrayList<OrderV2>();
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
            return p_cart;
        }



    public List<OrderV2> GetProducts() {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        final List<OrderV2> p_cart = new ArrayList<OrderV2>();
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
        return p_cart;
    }




}
