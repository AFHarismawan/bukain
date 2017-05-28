package in.buka.app.libs.utils;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import in.buka.app.libs.configs.Constants;

/**
 * Created by A. Fauzi Harismawan on 22/05/2017.
 */

public class HttpUtils {

    public static String GET_REQUEST = "GET";
    public static String POST_REQUEST = "POST";

    private HttpURLConnection conn = null;
    private final int TIMEOUT = 10000;

    private static class SingletonHolder {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return HttpUtils.SingletonHolder.INSTANCE;
    }

    public String  invoke(String method, String uri, String query) {
        return invokeWithAuth(method, uri, query, null, null);
    }

    public String invokeWithAuth(String method, String uri, String query, String username, String token) {
        String result = null;
        URL ur;
        try {
            ur = new URL(Constants.SERVER_URL + uri);
            conn = (HttpURLConnection) ur.openConnection();

            if(username != null) {
                String credentials = (username + ":" + token);
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                conn.setRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
            }
            conn.setRequestMethod(method);

            conn.setReadTimeout(TIMEOUT);
            conn.setConnectTimeout(TIMEOUT);

            if (method.equals(POST_REQUEST)) {
                OutputStreamWriter streamWriter = new OutputStreamWriter(conn.getOutputStream());
                streamWriter.write(query);
                streamWriter.flush();
                conn.setDoOutput(true);
            }

            int status = conn.getResponseCode();

            if (status >= 200 && status < 300) {
                result = readStream(conn.getInputStream());
            } else {
                Log.d(Constants.TAG, conn.getResponseMessage());
                result = readStream(conn.getErrorStream());
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return "{\"status\": \"ERROR\", \"message\": \"Device Connection Error\"}";
    }

    private String readStream(InputStream is) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("US-ASCII")));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            total.append(line);
        }
        reader.close();
        return total.toString();
    }
}
