package tk.matheuslucena.realidade.adapter;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tk.matheuslucena.realidade.Objetos.OrderV2;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;

public class OrderV2Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<OrderV2> itens = new ArrayList<OrderV2>();
    private int index= 0;
    private int top = 0;
    Activity act;
    //public ProductV3Adapter(Activity act, List<Order> itens)

    public OrderV2Adapter(Activity act, List<OrderV2> itens)
    {
        this.act = act;
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(act.getApplicationContext());
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */

    public int getCount()
    {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */

    public Product getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posção.
        final Product item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.orderv2_item, null);

        notifyDataSetChanged();
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        final TextView nome = view.findViewById(R.id.txt_nome);
        nome.setText(item.getName());

        final TextView data = view.findViewById(R.id.txt_data);
        data.setText("R$" + item.getPrice());

        final TextView valor = view.findViewById(R.id.txt_value);
        valor.setText(item.getDescription());

        /*

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nome.setText(item.getName());
                data.setText("R$" + item.getPrice());
                valor.setText(item.getDescription());
            }
        });
        */

        return view;
    }



}
