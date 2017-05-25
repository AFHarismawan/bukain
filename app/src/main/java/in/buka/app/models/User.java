package in.buka.app.models;

import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

import in.buka.app.models.structure.FirebaseModel;

/**
 * Created by Shade on 5/22/17.
 */

public class User extends FirebaseModel{

    private static String table = "users";

    public int id;
    public String name;
    public String token;
    public String email;
    public String phone;
    public String avatar;
    public String avatar_id;
    public Bank bank;
    public String omnikey;

    public @Nullable Integer backedProjectsCount;
    public @Nullable Integer createdProjectsCount;
    public @Nullable Boolean gamesNewsletter;
    public @Nullable Boolean happeningNewsletter;
    public @Nullable String location;

    public void getDataFromBL(){

    }

    public static DatabaseReference get(String id) {
        return firebase.getReference(table).child(id);
    }

    public static DatabaseReference get() {
        return firebase.getReference(table);
    }
}
