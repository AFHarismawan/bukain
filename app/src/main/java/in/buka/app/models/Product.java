package in.buka.app.models;

import in.buka.app.libs.services.BLService;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shade on 5/23/17.
 */

public class Product {

    public String id;
    public String category;
    public String[] category_structure;
    public String name;
    public String city;
    public String province;
    public int price;
    public int weight;
    public String courier; //as in JNE REG, ...
    public String[] images;
    public String url;
    public String desc;
    public String condition; //as in new or used
    public String[] specs;
    public boolean favorited;
    public String[] state;
    public String[] product_sin;

    public Product(JSONObject json){
        try {
            this.id = Integer.toString(json.getInt("id"));
            this.name = json.getString("name");
            this.category = json.getString("category");
            this.price = json.getInt("price");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
