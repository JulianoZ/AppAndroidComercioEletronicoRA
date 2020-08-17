package tk.matheuslucena.realidade.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;

import static android.content.ContentValues.TAG;

public class ProductV2Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Product> itens;
    private int index= 0;
    private int top = 0;
    Activity act;

    public ProductV2Adapter(Activity act, List<Product> itens)
    {
        this.act = act;
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(act.getApplicationContext());

        Log.d(TAG, "ProductV2Adapter: ");
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


    public View getView(final int position, View view, ViewGroup parent)
    {

        /*
        //Pega o item de acordo com a posção.
        final Product item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.productv2_item, null);

        notifyDataSetChanged();
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        final TextView nome = view.findViewById(R.id.txt_nome);
        final TextView preco = view.findViewById(R.id.txt_preco);
        final TextView id = view.findViewById(R.id.txt_id);

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nome.setText("teste 01");
                preco.setText("teste 02");
                id.setText("teste 03");
                //nome.setText(item.getName());
                //preco.setText("R$" +item.getPrice());
                //id.setText(item.getIdProduct());
            }
        });
        */

        return view;
    }





}
