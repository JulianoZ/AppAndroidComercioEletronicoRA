package tk.matheuslucena.realidade.adapter;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class ListDevices extends ListActivity {
    BluetoothAdapter bluetoothAdapter = null;
    Set<BluetoothDevice> paired_devices;
    ArrayList<BluetoothDevice> listdevices = new ArrayList <BluetoothDevice>();
    public static String ENDERECO_MAC = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        paired_devices = bluetoothAdapter.getBondedDevices();
        ArrayAdapter<String> arraybt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        if(paired_devices.size() > 0){
            for(BluetoothDevice device : paired_devices){
                String name_bt = device.getName();
                arraybt.add(name_bt);
                listdevices.add(device);
            }
        }
        setListAdapter(arraybt);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String mac = listdevices.get(position).getAddress();
        //Toast.makeText(getApplicationContext(), "Informations " + mac, Toast.LENGTH_SHORT).show();
        Intent return_mac = new Intent();
        return_mac.putExtra(ENDERECO_MAC,mac);
        setResult(RESULT_OK,return_mac);
        finish();
    }
}
