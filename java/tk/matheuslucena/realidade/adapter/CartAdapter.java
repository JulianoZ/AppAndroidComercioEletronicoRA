package tk.matheuslucena.realidade.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Objetos.Cart;
import tk.matheuslucena.realidade.Objetos.Product;
import tk.matheuslucena.realidade.R;
import tk.matheuslucena.realidade.dao.DAOCart;

import static tk.matheuslucena.realidade.Activities.MainActivity.EndPointWsRest;


public class CartAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Cart> itens = new ArrayList<Cart>();
    private int index= 0;
    private int top = 0;
    Activity act;
    TextView textView;
    public CartAdapter(Activity act, List<Cart> itens)
    {
        this.act = act;
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(act.getApplicationContext());
    }

    public CartAdapter(Activity act, List<Cart> itens,TextView textView)
    {
        this.act = act;
        this.textView = textView;
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
        Cart item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.list_cart, null);

        notifyDataSetChanged();
        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        TextView quantidade = (TextView)view.findViewById(R.id.prod_qnt);
        quantidade.setText("x" + Integer.toString(item.GetQuanty()));
        ((TextView) view.findViewById(R.id.prod_title)).setText(item.getName());
        ((TextView) view.findViewById(R.id.prod_price)).setText(String.valueOf(item.getPrice()));
        //((ImageView) view.findViewById(R.id.prod_img)).setImageBitmap(item.getImg());
        Picasso.with(act.getApplicationContext()).load(EndPointWsRest + "/Images/Products/" + item.getPicture1()).into((ImageView)view.findViewById(R.id.prod_img));

        //Controll to client: bought or not
        String bought="NÃO";
        if (item.getOrdered() == 1){
            bought = "SIM";
            ((Button) view.findViewById(R.id.btn_remove)).setVisibility(View.INVISIBLE);
        }

        TextView tv3 = (TextView)view.findViewById(R.id.sub_price);
        tv3.setText("Sub: R$ " + Double.toString((item.GetQuanty() * item.getPrice())) + "\nComprado: " + bought);
        Button btn_remove = view.findViewById(R.id.btn_remove);
        final View finalView = view;
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOCart dao = new DAOCart(act);
                dao.RemoveProduct(itens.get(position));
                itens.remove(position);
                notifyDataSetChanged();
                int val = dao.CountProducts();
                textView.setText(String.valueOf(val));

            }
        });
        return view;
    }
}
