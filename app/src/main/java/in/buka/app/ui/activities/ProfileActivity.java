package in.buka.app.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.database.DatabaseHelper;
import in.buka.app.libs.services.BLService;
import in.buka.app.libs.utils.JsonUtils;
import in.buka.app.models.User;

/**
 * Created by A. Fauzi Harismawan on 24/05/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout root;
    private ProgressDialog progress;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(R.string.profile);
        }

        root = (LinearLayout) findViewById(R.id.container_profile);

        getData();
    }

    private void getData() {
        DatabaseHelper helper = new DatabaseHelper(this);
        User user = helper.getCredentials();
        Bundle send = new Bundle();
        send.putString(BLService.KEY_URL, Constants.PROFILE_URL);
        send.putString(BLService.KEY_DATA, "");
        send.putString(BLService.KEY_TYPE, BLService.TYPE_AUTH);
        send.putString(BLService.KEY_USERNAME, Integer.toString(user.id));
        send.putString(BLService.KEY_PASSWORD, user.token);
        progress = ProgressDialog.show(this, "", "Loading...", true, false);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle recv = intent.getExtras();

                JSONObject response = new JSONObject(recv.getString(BLService.KEY_RESPONSE));
                if (response.getString("message").equals("null")) {
                    user = JsonUtils.parseUserProfile(response);

                } else {
                    Snackbar.make(root, response.getString("message"), Snackbar.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progress.dismiss();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("in.buka.app.REQUEST_COMPLETE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
