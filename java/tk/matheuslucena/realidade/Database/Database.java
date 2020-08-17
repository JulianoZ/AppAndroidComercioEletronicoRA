package tk.matheuslucena.realidade.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    private static final int db_version = 1;
    private static Database instancia;

    public Database(Context context) {
        super(context, "fasghjBASdASDkuytrsd", null,db_version );
    }
    public static synchronized Database getInstance(Context context)
    {
        if(instancia == null)
            instancia = new Database(context);
        return instancia;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_product = "CREATE TABLE IF NOT EXISTS product(" +
                "id INTEGER," +
                "name VARCHAR(45)," +
                "description VARCHAR(200)," +
                "price FLOAT(10)," +
                "short_description VARCHAR(50)," +
                "stock INT(10)," +
                "featured INT(1)," +
                "weight FLOAT(10)," +
                "picture VARCHAR(60)," +
                "picture2 VARCHAR(60)," +
                "subcat_id INT(5)," +
                "ar VARCHAR(5)" +
                ");";

        db.execSQL(query_product);

        String query_key = "CREATE TABLE IF NOT EXISTS primarykey_client(" +
                "key_client INTEGER)";

        db.execSQL(query_key);

        String queryClient = "CREATE TABLE IF NOT EXISTS client(" +
                "id_local INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idClient INTEGER," +
                "Name VARCHAR(45)," +
                "Email VARCHAR(45)," +
                "Password VARCHAR(200)," +
                "StreetName VARCHAR(60)," +
                "Complement VARCHAR(120)," +
                "Number VARCHAR(10)," +
                "ZipCode VARCHAR(60)," +
                "NameNeighborhood VARCHAR(50)," +
                "NameCity VARCHAR(60)," +
                "NameState VARCHAR(60)," +
                "IP VARCHAR(20)" +
                ");";

        db.execSQL(queryClient);

        String query_cart = "CREATE TABLE IF NOT EXISTS cart(" +
                "id INTEGER,"+
                "quanty INTEGER," +
                "ordered INTEGER" +
                ");";

        db.execSQL(query_cart);
        Log.d(" query_cart: ", query_cart);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS client");
        onCreate(db);
    }
}
