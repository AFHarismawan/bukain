package in.buka.app.libs.services;

/**
 * Created by Shade on 5/23/17.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import in.buka.app.R;

public class FirebaseService {
    private DatabaseReference dataReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final static String USERS_PATH = "users";

    private static class SingletonHolder {
        private static final FirebaseService INSTANCE = new FirebaseService();
    }

    public static FirebaseService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseService(){
        dataReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("BUKAIN", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("BUKAIN", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public FirebaseUser user(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String getAuthUserEmail() {
        FirebaseUser user = user();
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


    //Authorization
    public void signInFirebaseUser(final String email, final String password, Activity activity) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("BUKAIN", "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("BUKAIN", "signInWithEmail:failed", task.getException());
                            //@// TODO: 24/05/2017 : iki lo ul
                        }
                    }
                });
    }

    public void registerFirebaseUser(String email, String password, Activity activity) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("BUKAIN", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w("BUKAIN", "signInWithEmail:failed", task.getException());
                        }
                    }
                });
    }

    public void resumeAuth() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void pauseAuth() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
