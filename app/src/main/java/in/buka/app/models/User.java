package in.buka.app.models;

import android.support.annotation.Nullable;

/**
 * Created by Shade on 5/22/17.
 */

public class User {
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
}
