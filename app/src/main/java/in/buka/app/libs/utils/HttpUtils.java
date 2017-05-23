package in.buka.app.libs.utils;

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
