package in.buka.app.libs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.buka.app.models.User;

/**
 * Created by A. Fauzi Harismawan on 23/05/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "session";
    private static final int DATABASE_VERSION = 1;

    private final String TABLE_CRED = "cred";
    private final String COLUMN_USER_ID = "user_id";
    private final String COLUMN_USER_NAME = "user_name";
    private final String COLUMN_TOKEN = "token";
    private final String COLUMN_EMAIL = "email";
    private final String COLUMN_OMNIKEY = "omnikey";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CRED + " ("
                + COLUMN_USER_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_USER_NAME + " VARCHAR NOT NULL, "
                + COLUMN_TOKEN + " VARCHAR NOT NULL, "
                + COLUMN_EMAIL + " VARCHAR NOT NULL, "
                + COLUMN_OMNIKEY + " VARCHAR NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRED);
    }

    public void setCredentials(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, user.id);
        values.put(COLUMN_USER_NAME, user.name);
        values.put(COLUMN_EMAIL, user.email);
        values.put(COLUMN_TOKEN, user.token);
        values.put(COLUMN_OMNIKEY, user.omnikey);
        getWritableDatabase().replace(TABLE_CRED, null, values);
    }

    public User getCredentials() {
        Cursor mCursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_CRED, null);
        mCursor.moveToFirst();
        User user = new User();
        if (mCursor.getCount() != 0) {
            user.id = mCursor.getInt(mCursor.getColumnIndex(COLUMN_USER_ID));
            user.name = mCursor.getString(mCursor.getColumnIndex(COLUMN_USER_NAME));
            user.email = mCursor.getString(mCursor.getColumnIndex(COLUMN_EMAIL));
            user.token = mCursor.getString(mCursor.getColumnIndex(COLUMN_TOKEN));
            user.omnikey = mCursor.getString(mCursor.getColumnIndex(COLUMN_OMNIKEY));
        }

        mCursor.close();
        return user;
    }
}
