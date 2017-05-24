package in.buka.app.models.structure;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Shade on 5/23/17.
 */

public abstract class FirebaseModel {
    protected static FirebaseDatabase firebase = FirebaseDatabase.getInstance();

    protected void save(){

    }
}