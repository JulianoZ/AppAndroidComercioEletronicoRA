package tk.matheuslucena.realidade;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class SendName extends AsyncTask<String,Integer,Integer> {
    private DataClient dataClient;
    String name;
    Activity act;
    private static final String COUNT_KEY = "com.example.key.count";

    @Override
    protected Integer doInBackground(String... strings) {
        dataClient = Wearable.getDataClient(act);
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count");
        putDataMapReq.getDataMap().putString(COUNT_KEY, name);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Task<DataItem> putDataTask = dataClient.putDataItem(putDataReq);
        return null;
    }

    public SendName(String name, Activity act) {
        this.name = name;
        this.act = act;
    }
}
