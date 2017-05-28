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
    public String name;
    public int price;
    public String courier; //as in JNE REG, ...
    public String image;
    public String desc;
    public int stock;
    public int category_id;

    public Product(JSONObject json){
        try {
            this.id = json.getString("id");
            this.name = json.getString("name");
            this.category = json.getString("category");
            this.price = json.getInt("price");
            this.stock = json.getInt("stock");
            this.category_id = json.getInt("category_id");
            this.courier = json.getString("courier");
            this.image = json.getJSONArray("small_images").getString(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
