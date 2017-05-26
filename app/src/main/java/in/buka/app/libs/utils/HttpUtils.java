package in.buka.app.libs.utils;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import in.buka.app.ui.activities.ActivityFeedActivity;

/**
 * Created by A. Fauzi Harismawan on 22/05/2017.
 */

public class HttpUtils {

    public static String GET_REQUEST = "GET";
    public static String POST_REQUEST = "POST";

    public static String sendBasicAuthRequest(String url, String type, String data, String username, String password) {
        String credentials = (username + ":" + password);
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        URL ur;
        HttpURLConnection conn = null;
        try {
            ur = new URL(url);
            conn = (HttpURLConnection) ur.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
            conn.setDoOutput(true);
            conn.setRequestMethod(type);

            OutputStreamWriter streamWriter = new OutputStreamWriter(conn.getOutputStream());
            streamWriter.write(data);
            streamWriter.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                Log.d(ActivityFeedActivity.TAG, sb.toString());

            } else {
                Log.d(ActivityFeedActivity.TAG, conn.getResponseMessage());
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

    public static String sendPOSTRequest(String url, String data) {
        URL ur;
        HttpURLConnection conn = null;
        try {
            ur = new URL(url);
            conn = (HttpURLConnection) ur.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            OutputStreamWriter streamWriter = new OutputStreamWriter(conn.getOutputStream());
            streamWriter.write(data);
            streamWriter.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                Log.d(ActivityFeedActivity.TAG, sb.toString());

            } else {
                Log.d(ActivityFeedActivity.TAG, conn.getResponseMessage());
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }
}
