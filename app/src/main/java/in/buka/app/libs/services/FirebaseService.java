package in.buka.app.libs.services;

/**
 * Created by Shade on 5/23/17.
 */

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseService {
    private DatabaseReference dataReference;
    private final static String USERS_PATH = "users";

    private static class SingletonHolder {
        private static final FirebaseService INSTANCE = new FirebaseService();
    }

    public static FirebaseService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseService(){
        dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }

    public DatabaseReference getUserReference(String uid){
        DatabaseReference userReference = null;
        if (uid != null) {
            userReference = dataReference.getRoot().child(USERS_PATH).child(uid);
        }
        return userReference;
    }

    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserEmail());
    }
}
