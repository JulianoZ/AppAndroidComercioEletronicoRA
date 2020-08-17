package tk.matheuslucena.realidade.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import tk.matheuslucena.realidade.Database.Database;
import tk.matheuslucena.realidade.Objetos.OrderV2;
import tk.matheuslucena.realidade.Objetos.Product;

public class DAOProduct {
    Context context;
    public DAOProduct(Context context){
        this.context = context;
    }
    public DAOProduct(){

    }

    public String inserir(Product product) {
        ContentValues valores;
        long resultado;
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();

        try {
            valores = new ContentValues();
            valores.put("id", product.getIdProduct());
            valores.put("name", product.getName());
            valores.put("picture",product.getPicture1());
            valores.put("price",product.getPrice());
            valores.put("ar",product.getAR());
            resultado = db.insert("product", null, valores);
        }catch (Exception e){
            resultado = 0;
        }finally {
            db.close();
        }
        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public List<Product> getProducts(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        List<Product> product = new ArrayList<>();
        try{
            String q = "SELECT * FROM product";
            Cursor mCursor = db.rawQuery(q, null);
            mCursor.moveToFirst();
            while(true){
                Product pdt = new Product();
                pdt.setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
                pdt.setName(mCursor.getString(mCursor.getColumnIndex("name")));
                pdt.setPicture1(mCursor.getString(mCursor.getColumnIndex("picture")));
                pdt.setPrice(mCursor.getFloat(mCursor.getColumnIndex("price")));
                pdt.setAR(mCursor.getString(mCursor.getColumnIndex("ar")));
                product.add(pdt);
                if(mCursor.isLast())break;
                mCursor.moveToNext();
            }

            mCursor.close();
        }catch (Exception e){

        }finally {
            db.close();
        }
        return product;
    }


    public Product SelectSpecificIdObject(Product objProduct, OrderV2 objOrder){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        Product pdt = new Product();
        try{
            //Log.d("SelectSpecificIdObject ", " id Prod> " + objOrder.getIdProduct());
            String q = "SELECT * FROM product where id = " + objOrder.getIdProduct();
            Cursor mCursor = db.rawQuery(q, null);
            mCursor.moveToFirst();
            while(true){
                //Log.d("SelectSpecificIdObject ", " coluna 1 > " + mCursor.getColumnIndex("id") + mCursor.getColumnIndex("name"));
                //Product pdt = new Product();
                pdt.setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
                pdt.setName(mCursor.getString(mCursor.getColumnIndex("name")));
                pdt.setPicture1(mCursor.getString(mCursor.getColumnIndex("picture")));
                pdt.setPrice(mCursor.getFloat(mCursor.getColumnIndex("price")));
                pdt.setAR(mCursor.getString(mCursor.getColumnIndex("ar")));

                if(mCursor.isLast())break;
                mCursor.moveToNext();
            }

            mCursor.close();
        }catch (Exception e){

        }finally {
            db.close();
        }
        return pdt;
    }

    public List<Product> getAR(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        List<Product> product = new ArrayList<>();
        try{
            String q = "SELECT id, name,* FROM product";
            Cursor mCursor = db.rawQuery(q, null);
            mCursor.moveToFirst();
            while(true){
                if(mCursor.getString(mCursor.getColumnIndex("ar")).equals("true")) {
                    Product pdt = new Product();
                    pdt.setIdProduct(mCursor.getInt(mCursor.getColumnIndex("id")));
                    pdt.setName(mCursor.getString(mCursor.getColumnIndex("name")));
                    pdt.setPicture1(mCursor.getString(mCursor.getColumnIndex("picture")));
                    pdt.setPrice(mCursor.getFloat(mCursor.getColumnIndex("price")));
                    product.add(pdt);
                    if(mCursor.isLast())break;
                }
                mCursor.moveToNext();
            }
            mCursor.close();
        }catch (Exception e){

        }finally {
            db.close();
        }
        return product;
    }
    public void removeAllProducts(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String q = "delete FROM product";
        try {
            db.execSQL(q);
        }catch (Exception e){

        }finally {
            db.close();
        }
    }

    public void removeProduct(Product product) {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            String q = "delete FROM product where id = " + product.getIdProduct();
            db.execSQL(q);        }catch (Exception e){

        }finally {
            db.close();
        }
    }
}
