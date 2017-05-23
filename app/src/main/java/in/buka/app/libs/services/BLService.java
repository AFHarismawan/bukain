package in.buka.app.libs.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import in.buka.app.libs.utils.HttpUtils;

/**
 * Created by A. Fauzi Harismawan on 22/05/2017.
 */

public class BLService extends Service {

    public static String KEY_URL = "url";
    public static String KEY_JSON = "json";
    public static String KEY_RESPONSE = "response";

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        Bundle recv = intent.getExtras();
        String url = recv.getString(KEY_URL);
        String json = recv.getString(KEY_JSON);

        String response = HttpUtils.sendJSONtoServer(url, json);

        //send broadcast
        Intent i = new Intent("in.buka.app.REQUEST_COMPLETE");
        Bundle send = new Bundle();
        send.putString(KEY_RESPONSE, response);
        i.putExtras(send);

        sendBroadcast(i);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
