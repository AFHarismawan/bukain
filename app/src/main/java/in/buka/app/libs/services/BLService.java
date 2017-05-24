package in.buka.app.libs.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import in.buka.app.libs.utils.HttpUtils;

/**
 * Created by A. Fauzi Harismawan on 22/05/2017.
 */

public class BLService extends Service {

    public static String KEY_TYPE = "type";
    public static String TYPE_LOGIN = "login";

    public static String KEY_URL = "url";
    public static String KEY_DATA = "data";
    public static String KEY_USERNAME = "username";
    public static String KEY_PASSWORD = "password";
    public static String KEY_RESPONSE = "response";

    @Override
    public int onStartCommand(final Intent intent,  int flags, int startId) {
        new AsyncTask<Void, Void, Void>() {
            String response;

            @Override
            protected Void doInBackground(Void... params) {
                Bundle recv = intent.getExtras();
                String url = recv.getString(KEY_URL);
                String data = recv.getString(KEY_DATA);
                if (recv.getString(KEY_TYPE).equals(TYPE_LOGIN)) {
                    String username = recv.getString(KEY_USERNAME);
                    String password = recv.getString(KEY_PASSWORD);
                    response = HttpUtils.sendBasicAuthPOSTRequest(url, data, username, password);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //send broadcast
                Intent i = new Intent("in.buka.app.REQUEST_COMPLETE");
                Bundle send = new Bundle();
                send.putString(KEY_RESPONSE, response);
                i.putExtras(send);

                sendBroadcast(i);
            }
        }.execute();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
