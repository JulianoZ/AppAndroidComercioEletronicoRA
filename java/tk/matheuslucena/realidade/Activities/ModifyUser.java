package tk.matheuslucena.realidade.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.matheuslucena.realidade.Objetos.Client;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.RestInterface;
import tk.matheuslucena.realidade.dao.DAOCliente;

public class ModifyUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText nome = findViewById(R.id.edt_name);
        final EditText email = findViewById(R.id.edt_email);
        final EditText pass = findViewById(R.id.edt_senha);
        DAOCliente dao = new DAOCliente(getApplicationContext());
        final Client c = dao.getlasid();
        if(c.getName() != null)
        nome.setHint(c.getName());
        if(c.getEmail() != null)
        email.setHint(c.getEmail());
        Button cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(ModifyUser.this, MainActivity.class);
                startActivity(main);
            }
        });
        Button alterar = findViewById(R.id.btn_confirm);
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DAOCliente dao = new DAOCliente(getApplicationContext());
                boolean altera = false;
                if(nome.length() > 4 && nome.length() > 0 && !nome.getText().toString().equals(c.getName())){
                    c.setName(nome.getText().toString());
                    altera = true;
                }
                if(email.getText().toString().contains("@") && email.length() > 5 && !email.getText().toString().equals(c.getEmail())) {
                    c.setEmail(nome.getText().toString());
                    altera = true;
                }
                if(pass.length() > 4 && pass.length() > 0 && !pass.getText().toString().equals(c.getPassword())) {
                    c.setPassword(pass.getText().toString());
                    altera = true;
                }
                if(altera) {
                    try{
                        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MainActivity.EndPointWsRest).build();
                        RestInterface api = adapter.create(RestInterface.class);
                        api.updateUser(generate_json(c.getId(),c.getName(), c.getEmail(),c.getPassword()),
                                new Callback<Response>() {
                                    @Override
                                    public void success(Response response, Response response2) {
                                        BufferedReader reader = null;
                                        String output = "";
                                        try{
                                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                                            output = reader.readLine();
                                            if(output.equals("OK")){
                                                dao.inserir(c);
                                            }
                                            else Toast.makeText(ModifyUser.this,"Erro ao atualizar o usuario, tente novamente",Toast.LENGTH_LONG).show();
                                        }catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Log.d("JSON", output);
                                    }
                                    @Override
                                    public void failure(RetrofitError error) {
                                    }
                                }
                        );
                        Toast.makeText(ModifyUser.this,"Usuario atualizado com sucesso", Toast.LENGTH_LONG).show();
                        Intent main = new Intent(ModifyUser.this, MainActivity.class);
                        startActivity(main);
                    }catch (Exception e){
                        Toast.makeText(ModifyUser.this,"Falha ao atualizar o usuario, tente novamente",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private String generate_json(int id, String Nome, String Email, String password){
        try{
            JSONObject cart = new JSONObject();
            cart.put("idClient",id);
            cart.put("Name",Nome);
            cart.put("Email", Email);
            cart.put("Password", password);
            return cart.toString();
        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
