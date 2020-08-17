package tk.matheuslucena.realidade.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Activities.MainActivity;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.TCPClient;
import tk.matheuslucena.realidade.dao.DAOCart;

import android.app.Activity;

import com.squareup.picasso.Picasso;

import static tk.matheuslucena.realidade.Activities.MainActivity.EndPointWsRest;

public class ProductAdapter extends BaseAdapter implements View.OnClickListener{
    private LayoutInflater mInflater;
    private List<Product> itens;
    Activity act;
    TextView textView;
    public ProductAdapter(Activity act,List<Product> itens) {
        this.act = act;
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(act.getApplicationContext());
    }

    public ProductAdapter(Activity act,List<Product> itens,TextView textView) {
        this.act = act;
        this.textView = textView;
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(act.getApplicationContext());
    }

    public void ChangeList( List<Product> itens){
        this.itens = itens;
    }
    public int getCount() {
        return itens.size();
    }

    public Product getItem(int position) {
        return itens.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        final Product item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.list_products, null);

        ((TextView) view.findViewById(R.id.prod_title)).setText(item.getName());
        ((TextView) view.findViewById(R.id.prod_price)).setText("R$ " + item.getPrice());
        Picasso.with(act.getApplicationContext()).load(EndPointWsRest + "/Images/Products/" + item.getPicture1()).into((ImageView)view.findViewById(R.id.prod_img));

        ImageButton btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog(itens.get(position)).show();
            }
        });

        //view.setOnClickListener(this);
        //view.setTag(String.valueOf(position));
        return view;
    }
    private AlertDialog getDialog(final Product p){
        LayoutInflater inflater = (LayoutInflater)
                act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NumberPicker npView = new NumberPicker(act);
        npView.setMinValue(1);
        npView.setMaxValue(20);
        return new AlertDialog.Builder(act)
                .setTitle("Selecione a quantidade")
                .setView(npView)
                .setPositiveButton("Adicionar ao Carrinho",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DAOCart dao = new DAOCart(act);
                                dao.inserirProduct(p,npView.getValue());
                                DAOCart daoCart = new DAOCart(act);
                                textView.setText(String.valueOf(daoCart.CountProducts()));
                                Toast.makeText(act, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                .create();
    }

    @Override
    public void onClick(View v) {
        int pos = Integer.parseInt(v.getTag().toString());
        TCPClient.send_recv("selectbyname_" + itens.get(pos).getName());
    }
}