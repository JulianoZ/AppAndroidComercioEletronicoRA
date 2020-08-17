package tk.matheuslucena.realidade.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import tk.matheuslucena.realidade.Database.Database;
import tk.matheuslucena.realidade.Objetos.Client;

public class DAOCliente implements Serializable {
    Context context;
    int primaryKey;
    public DAOCliente(Context context){
        this.context = context;
    }
    public void SetPrimaryKey(){ //Used in CartFragment class inside the method makeOrderSetTable
        Random rnd = new Random();
        int num = rnd.nextInt(100000);
        primaryKey = num;

        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues valores;
        valores = new ContentValues();
        valores.put("key_client",num);

        Log.d("INSERINDO " , String.valueOf(primaryKey));
        db.insert("primarykey_client", null, valores);
        db.close();

    }

    public void ChangeKey(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String q = "delete from primarykey_client";
        db.execSQL(q);
        SetPrimaryKey();
        db.close();

    }
    public int GetPrimaryKey(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "SELECT * FROM primarykey_client";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        int key = c.getInt(c.getColumnIndex("key_client"));
        Log.d("KEY KEY ", String.valueOf(key));        db.close();

        return key;
    }

    public int GetCount(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM primarykey_client";
        Cursor c = db.rawQuery(query,null);
        Log.d("COUNT ", String.valueOf(c.getCount()));        db.close();

        return c.getCount();
    }

    public String inserir(Client client){
        long resultado = 0;
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            ContentValues valores;
            valores = new ContentValues();
            valores.put("idClient",client.getId());
            valores.put("Name",client.getName());
            valores.put("Email",client.getEmail());
            valores.put("Password",client.getPassword());
            resultado = db.insert("client", null, valores);
        }catch (Exception e){

        }finally {
            db.close();
        }
        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public void Logout(){
        ContentValues valores;
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            valores = new ContentValues();
            valores.put("Name", (String) null);
            valores.put("Email", (String) null);
            valores.put("Password", (String) null);

            db.insert("client", null, valores);        }catch (Exception e){

        }finally {
            db.close();
        }
    }

    public List<Client> getClients(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        List <Client> list = new ArrayList<Client>();

        try {
            String query = "SELECT * FROM client";
            Cursor c = db.rawQuery(query,null);
            while(c != null){
                Client cliente = new Client();
                cliente.setId(c.getInt(c.getColumnIndex("idClient")));
                list.add(cliente);
                c.moveToNext();
            }
        }catch (Exception e){

        }finally {
            db.close();
        }
        return list;
    }

    public Client getlasid() {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        Client cliente = new Client();
        try {
            String q = "SELECT MAX(id_local),* FROM client";
            Cursor mCursor = db.rawQuery(q, null);

            mCursor.moveToFirst();

            cliente.setId(mCursor.getInt(mCursor.getColumnIndex("MAX(id_local)")));
            cliente.setName(mCursor.getString(mCursor.getColumnIndex("Name")));
            cliente.setEmail(mCursor.getString(mCursor.getColumnIndex("Email")));
            cliente.setPassword(mCursor.getString(mCursor.getColumnIndex("Password")));

        }catch (Exception e){

        }finally {
            db.close();
        }
        return cliente;
    }
    public void RemoveAll(){
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            String q = "delete from client";
            db.execSQL(q);
        }catch (Exception e){

        }finally {
            db.close();
        }
    }
    public void UpdateClient(Client client) {
        Database database = Database.getInstance(context);
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            String q = "UPDATE client SET Name='"+client.getName()+"'," +
                    "Email='"+ client.getEmail()+"',"+
                    "Password='" + client.getPassword()+"' "+
                    "WHERE Email='"+client.getEmail()+"'";
            db.execSQL(q);
        }catch (Exception e){

        }finally {
            db.close();
        }
    }
}