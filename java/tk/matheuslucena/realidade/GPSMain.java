package tk.matheuslucena.realidade;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import tk.matheuslucena.realidade.Activities.GPSV2Activity;

public class GPSMain extends AppCompatActivity {



    public List<String> getDataLocation(Location location, List<String> data){

        List<String> dataLocationList = new ArrayList<String>();
        String getData;
                            for(int x=0;x<data.size();x++) {
                                getData = "";
                                if (location != null) {

                                    if(data.get(x) == "Latitude"){
                                        getData = String.valueOf(location.getLatitude());
                                        Log.d("GPS 1", " cond > " + data.get(x) + " " + getData + " " + x);
                                    }else if(data.get(x) == "Longitude") {
                                        getData = String.valueOf(location.getLongitude());
                                        Log.d("GPS 2", " cond > " + data.get(x) + " " + getData + " " + x);
                                    }
                                  dataLocationList.add(getData);
                                    Log.d("data location ", " data location > "+ dataLocationList.get(x));
                                } else {
                                    Log.d("LOCATION", "NULL");
                                }
                            }
        return dataLocationList;
    }

/*
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permissão GPS")
                        .setMessage("Por favor, ative seu gps.")
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(GPSMain.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }



    public void showSettingAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GPSMain.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS não está ativo, deseja ativar? (É necessario para realizar o pedido)  ");
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                GPSMain.this.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
*/

}
