package in.buka.app.models;

import android.support.annotation.Nullable;

/**
 * Created by Shade on 5/22/17.
 */

public class User {
    public @Nullable Integer backedProjectsCount;
    public @Nullable Integer createdProjectsCount;
    public @Nullable Boolean gamesNewsletter;
    public @Nullable Boolean happeningNewsletter;
    public int id;
    public @Nullable String location;
    public String name;
    public String token;
    public String email;
    public String omnikey;

    public void getDataFromBL(){

    }
}
