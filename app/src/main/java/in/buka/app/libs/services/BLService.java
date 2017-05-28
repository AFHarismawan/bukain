package in.buka.app.libs.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.database.DatabaseHelper;
import in.buka.app.libs.utils.HttpUtils;
import in.buka.app.models.User;

/**
 * Created by A. Fauzi Harismawan on 22/05/2017.
 */

public class BLService extends Service {

    private HttpUtils api = HttpUtils.getInstance();

    public static String KEY_TYPE = "type";
    public static String TYPE_LOGIN = "login";
    public static String TYPE_AUTH = "auth";
    public static String TYPE_NO_AUTH = "no-auth";

    public static String KEY_URL = "url";
    public static String KEY_DATA = "data";
    public static String KEY_USERNAME = "username";
    public static String KEY_PASSWORD = "password";
    public static String KEY_RESPONSE = "response";
    public static String KEY_REQUEST = "request";

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new AsyncTask<Void, Void, Void>() {
            String response;
            String url;

            @Override
            protected Void doInBackground(Void... params) {
                Bundle recv = intent.getExtras();
                url = recv.getString(KEY_URL);
                String data = recv.getString(KEY_DATA);
                String request = recv.getString(KEY_REQUEST);

                if (recv.getString(KEY_TYPE).equals(TYPE_LOGIN)) {
                    String username = recv.getString(KEY_USERNAME);
                    String password = recv.getString(KEY_PASSWORD);
                    response = api.invokeWithAuth(request, url, data, username, password);
                } else if (recv.getString(KEY_TYPE).equals(TYPE_AUTH)) {
                    DatabaseHelper helper = new DatabaseHelper(BLService.this);
                    User user = helper.getCredentials();
                    Log.d(Constants.TAG, user.id + ":" + user.token);

                    String username = Integer.toString(user.id);
                    String password = user.token;
                    response = api.invokeWithAuth(request, url, data, username, password);
                } else {
                    response = api.invoke(request, url, data);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //send broadcast
                Intent i = new Intent(Constants.REQUEST_COMPLETE_INTENT_FILTER);
                Bundle send = new Bundle();
                send.putString(KEY_URL, url);
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
