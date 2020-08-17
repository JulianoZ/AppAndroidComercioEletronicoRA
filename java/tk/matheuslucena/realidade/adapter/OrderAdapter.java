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

import tk.matheuslucena.realidade.Objetos.Order;
import tk.matheuslucena.realidade.R;

public class OrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Order> itens = new ArrayList<Order>();
    private int index= 0;
    private int top = 0;
    Activity act;
    public OrderAdapter(Activity act, List<Order> itens)
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

    public Order getItem(int position)
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
        final Order item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.order_item, null);

        notifyDataSetChanged();
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        final TextView nome = view.findViewById(R.id.txt_nome);
        final TextView data = view.findViewById(R.id.txt_data);
        final TextView valor = view.findViewById(R.id.txt_value);

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nome.setText(item.getName());
                //data.setText(String.valueOf(getDate(Long.parseLong(getOnlyNumerics(item.getDateTimeOrdered())))));
                data.setText(String.valueOf(item.getDateTimeOrdered2()));
                valor.setText("R$" +item.getValueTotal());
            }
        });



        return view;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }

    public static String getOnlyNumerics(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length() ; i++) {
            c = str.charAt(i);

            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }
}