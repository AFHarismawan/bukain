package in.buka.app.models;

import android.support.annotation.Nullable;

/**
 * Created by Shade on 5/22/17.
 */

public class Update {
    public @Nullable String body;
    public @Nullable Integer commentsCount;
    public long id; //id dan projectId idealnya selalu sama!
    public @Nullable Integer likesCount;
    public long projectId; //id dan projectId idealnya selalu sama!
    public long publishedAt;
    public int sequence;
    public String title;
    public long updatedAt;
    public @Nullable User user;
    public @Nullable Boolean visible;
    public String[] urls;

    public @Nullable Boolean hasLiked(){
        return null;
    }
}