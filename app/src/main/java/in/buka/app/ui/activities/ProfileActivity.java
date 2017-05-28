package in.buka.app.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.database.DatabaseHelper;
import in.buka.app.libs.services.BLService;
import in.buka.app.libs.utils.CircleTransformation;
import in.buka.app.libs.utils.HttpUtils;
import in.buka.app.libs.utils.JsonUtils;
import in.buka.app.models.Project;
import in.buka.app.models.User;
import in.buka.app.ui.adapters.ActivityFeedAdapter;

/**
 * Created by A. Fauzi Harismawan on 24/05/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    public static String KEY_ID = "uid";

    private LinearLayout root;
    private ProgressDialog progress;
    private ImageView avatar;
    private TextView name, created, backed;
    private RecyclerView recyclerView;

    private User user;
    private List<Project> projects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("");
        }

        root = (LinearLayout) findViewById(R.id.container_profile);

        getUserData();

    }

    private void initView() {
        avatar = (ImageView) findViewById(R.id.avatar_image_view);
        name = (TextView) findViewById(R.id.user_name_text_view);
        created = (TextView) findViewById(R.id.created_count_text_view);
        backed = (TextView) findViewById(R.id.backed_count_text_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Picasso.with(this)
                .load(user.avatar)
                .transform(new CircleTransformation())
                .placeholder(ContextCompat.getDrawable(this, R.drawable.gray_gradient))
                .into(avatar);

        name.setText(user.name);
    }

    private void getProjectData() {
        Project.get().orderByChild("uid").equalTo(user.id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Project project = postSnapshot.getValue(Project.class);
                    project.id = dataSnapshot.getKey();
                    projects.add(project);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        ActivityFeedAdapter adapter = new ActivityFeedAdapter(ProfileActivity.this, projects);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                        Log.d(Constants.TAG, Integer.toString(projects.size()));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUserData() {
        Intent service = new Intent(this, BLService.class);
        Bundle send = new Bundle();
        send.putString(BLService.KEY_URL, Constants.PROFILE_URL);
        send.putString(BLService.KEY_DATA, "");
        send.putString(BLService.KEY_TYPE, BLService.TYPE_AUTH);
        send.putString(BLService.KEY_REQUEST, HttpUtils.GET_REQUEST);
        service.putExtras(send);
        startService(service);
        progress = ProgressDialog.show(this, "", "Loading...", true, false);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle recv = intent.getExtras();
                JSONObject response = new JSONObject(recv.getString(BLService.KEY_RESPONSE));
                if (response.getString("status").equals("OK")) {
                    DatabaseHelper helper = new DatabaseHelper(ProfileActivity.this);
                    user = helper.getCredentials();
                    user.avatar = JsonUtils.parseUserProfile(response).avatar;

                    initView();
                    getProjectData();
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
        registerReceiver(receiver, new IntentFilter(Constants.REQUEST_COMPLETE_INTENT_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
