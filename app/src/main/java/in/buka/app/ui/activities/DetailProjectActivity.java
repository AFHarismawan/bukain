/**
 *  Halaman pertama kali buka APP:
 *  List Project dengan tabulasi (Baru, Terdanai, Hampir Berakhir dll) dan tombol search
 **/

package in.buka.app.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.database.DatabaseHelper;
import in.buka.app.libs.services.BLService;
import in.buka.app.libs.utils.HttpUtils;
import in.buka.app.libs.utils.JsonUtils;
import in.buka.app.models.Project;
import in.buka.app.models.User;
import in.buka.app.ui.adapters.ActivityFeedAdapter;
import in.buka.app.ui.adapters.DetailProjectAdapter;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailProjectActivity extends AppCompatActivity {

    protected RecyclerView projectRecyclerView;
    private Project project;

    public static String TAG = "BUKAIN";
    private RelativeLayout root;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        root = (RelativeLayout) findViewById(R.id.container_detail_project);

        projectRecyclerView = (RecyclerView) findViewById(R.id.project_recycler_view);

        Bundle bundle = getIntent().getExtras();
        Project.get(bundle.getString(ActivityFeedAdapter.KEY_ID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                project = dataSnapshot.getValue(Project.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        projectRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        DetailProjectAdapter adapter = new DetailProjectAdapter(DetailProjectActivity.this, project);
                        projectRecyclerView.setAdapter(adapter);
                        projectRecyclerView.setLayoutManager(new LinearLayoutManager(DetailProjectActivity.this));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(root, databaseError.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }

    private void getProducts() {
        Intent service = new Intent(this, BLService.class);
        Bundle send = new Bundle();
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("keyword", project.id);
        String query = builder.build().getEncodedQuery();

        send.putString(BLService.KEY_URL, Constants.PRODUCTS_URL);
        send.putString(BLService.KEY_DATA, query);
        send.putString(BLService.KEY_TYPE, BLService.TYPE_NO_AUTH);
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
                if (response.getString("message").equals("null")) {
                    User user = JsonUtils.parseUserCredentials(response);
                    DatabaseHelper helper = new DatabaseHelper(DetailProjectActivity.this);
                    helper.setCredentials(user);

                    User.get(Integer.toString(user.id)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dataSnapshot.getValue(User.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    Snackbar.make(root, response.getString("message"), Snackbar.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progress.dismiss();
        }
    };
}
