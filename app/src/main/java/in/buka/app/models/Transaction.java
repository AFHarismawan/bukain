package in.buka.app.models;

/**
 * Created by Shade on 5/23/17.
 */

public class Transaction {

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

    public Transaction(String id){
        // TODO: 5/26/17 Panggil service helper untuk mengambil data product dari BL.
    }

    
}
