package in.buka.app.models;

import android.support.annotation.Nullable;
import com.google.firebase.database.DatabaseReference;
import in.buka.app.models.structure.FirebaseModel;

/**
 * Created by Shade on 5/22/17.
 */

public class Update extends FirebaseModel{
    protected static String table = "updates";

    public @Nullable String body;
    public @Nullable Integer commentsCount;
    public String id; //id dan projectId idealnya selalu sama!
    public @Nullable Integer likesCount;
    public long projectId; //id dan projectId idealnya selalu sama!
    public long publishedAt;
    public int sequence;
    public String title;
    public long updatedAt;
    public @Nullable User user;
    public @Nullable Boolean visible;
    public String[] urls;


    //static methods
    public static DatabaseReference get(String id) {
        return firebase.getReference(table).child(id);
    }

    public static DatabaseReference get() {
        return firebase.getReference(table);
    }

    //relations
//    public Relation project(){
//        return new Relation(Update.class, Project.class, id);
//    }


    //Cast variable
    public @Nullable Boolean hasLiked(){
        return null;
    }
}