package in.buka.app.libs.utils;

import org.json.JSONException;
import org.json.JSONObject;

import in.buka.app.models.User;

/**
 * Created by A. Fauzi Harismawan on 23/05/2017.
 */

public class JsonUtils {

    public static User parseUserCredentials(JSONObject json) {
        try {
            User user = new User();
            user.id = json.getInt("user_id");
            user.name = json.getString("user_name");
            user.token = json.getString("token");
            user.email = json.getString("email");
            user.omnikey = json.getString("omnikey");

            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
