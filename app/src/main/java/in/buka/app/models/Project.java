package in.buka.app.models;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import in.buka.app.models.structure.FirebaseModel;

/**
 * Created by A. Fauzi Harismawan on 06/05/2017.
 */

public class Project extends FirebaseModel {
    private static String table = "projects";

    public String id;
    public int uid, category_id;
    public String image, video;
    public String category, name, desc;
    public long funded, modal;
    public int backers;
    public long deadline, timestamp;
    public List<Integer> products;
    public List<String> project_sin;

    //static methods
    public static DatabaseReference get(String id) {
        return firebase.getReference(table).child(id);
    }

    public static DatabaseReference get() {
        return firebase.getReference(table);
    }


    //relations
//    public Relation updates() {
//        return new Relation(Project.class, Update.class, id);
//    }


    // Cast Variables
    public String backers() {
        return Integer.toString(backers);
    }

    public int fundedPercent() {
        return (int) (funded * 100 / modal);
    }

    public String funded() {
        return Integer.toString(fundedPercent()) + "%";
    }
}