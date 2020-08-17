package tk.matheuslucena.realidade.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Database.Database;
import tk.matheuslucena.realidade.Objetos.Cart;
import tk.matheuslucena.realidade.Objetos.Client;
import tk.matheuslucena.realidade.Objetos.OrderV2;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.adapter.ProductAdapter;


public class DAOCart {
    Context context;


    public DAOCart(){

    }


    public DAOCart(Context context){
        this.context = context;
    }
    public String inserirProduct(Product product, int quanty){
        long resultado = 0;
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            ContentValues valores;
            valores = new ContentValues();
            valores.put("id",product.getIdProduct());
            valores.put("quanty",quanty);
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



    public String InsertObject(OrderV2 objOrder){
        long resultado = 0;
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            ContentValues valores;
            valores = new ContentValues();
            valores.put("id",objOrder.product.getIdProduct());
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


    public void RemoveAll(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String sql = "DELETE FROM cart";
        db.execSQL(sql);
        db.close();
    }

    public void RemoveProduct(Product product){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String sql = "DELETE FROM cart WHERE " + product.getIdProduct();
        db.execSQL(sql);
        db.close();
    }



    public Cart GetCart(int id){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String sql = "SELECT * FROM cart WHERE id="+id;
        Cursor mCursor = db.rawQuery(sql, null);
        mCursor.moveToFirst();
        if(mCursor.getCount() > 0) {
            Cart pdt = new Cart();

            pdt.SetQuanty(mCursor.getInt(mCursor.getColumnIndex("quanty")));
            pdt.setOrdered(mCursor.getInt(mCursor.getColumnIndex("ordered")));
            pdt.setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
            return pdt;
        }
        db.close();
        return null;
    }

    public void UpdateOrdered(int order){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String sql = "UPDATE cart SET ordered=" + order;
        db.execSQL(sql);
        db.close();
    }


    public List<Cart> OrderListSession(int SessionId, List<Float> listOrder) {
        int x;
        List<Cart> listOrderSession  = new ArrayList<Cart>();


        return listOrderSession;
    }

    public List<Cart> GetProducts() {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        final List<Cart> p_cart = new ArrayList<Cart>();
        try {
            String all_ids = "SELECT * FROM cart";
            Cursor mCursor = db.rawQuery(all_ids, null);
            mCursor.moveToFirst();
            while (true) {
                Cart cart = new Cart();
                cart.SetQuanty(mCursor.getInt(mCursor.getColumnIndex("quanty")));
                cart.setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
                cart.setOrdered(mCursor.getInt(mCursor.getColumnIndex("ordered")));
                p_cart.add(cart);
                if(mCursor.isLast())break;
                mCursor.moveToNext();
            }
            mCursor.close();
            mCursor = null;
            int cont = 0;
            for (final Cart p : p_cart) {
                String select_products = "SELECT * FROM product where id=" + p.getIdProduct();
                mCursor = db.rawQuery(select_products, null);
                mCursor.moveToFirst();

                final Cart pdt = new Cart();
                p_cart.get(cont).setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
                p_cart.get(cont).setName(mCursor.getString(mCursor.getColumnIndex("name")));
                p_cart.get(cont).setPrice(mCursor.getFloat(mCursor.getColumnIndex("price")));
                p_cart.get(cont).setPicture1(mCursor.getString(mCursor.getColumnIndex("picture")));
                p_cart.get(cont).SetQuanty(p.GetQuanty());
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




    public List<OrderV2> SelectObject(String ord) {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        final List<OrderV2> p_cart = new ArrayList<OrderV2>();
        try {
            String all_ids = "SELECT * FROM cart";
            Cursor mCursor = db.rawQuery(all_ids, null);
            mCursor.moveToFirst();
            while (true) {
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

                final Cart pdt = new Cart();
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


    public int CountProducts() {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        int quant = 0;
        try {
            String all_ids = "SELECT COUNT(*) FROM cart";
            Cursor mCursor = db.rawQuery(all_ids, null);
            mCursor.moveToFirst();
            while (true) {
                quant = mCursor.getInt(mCursor.getColumnIndex("COUNT(*)"));
                if(mCursor.isLast())break;
                mCursor.moveToNext();
            }
            mCursor.close();
            mCursor = null;
            assert mCursor != null;
            mCursor.close();

        } catch (Exception e) {
            Log.d("ERROR ", String.valueOf(e));
        } finally {
            db.close();
        }
        return quant;
    }
}
