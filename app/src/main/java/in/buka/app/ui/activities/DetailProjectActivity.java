/**
 * Halaman pertama kali buka APP:
 * List Project dengan tabulasi (Baru, Terdanai, Hampir Berakhir dll) dan tombol search
 **/

package in.buka.app.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.libs.services.BLService;
import in.buka.app.libs.utils.HttpUtils;
import in.buka.app.libs.utils.JsonUtils;
import in.buka.app.models.Product;
import in.buka.app.models.Project;
import in.buka.app.models.User;
import in.buka.app.ui.adapters.ActivityFeedAdapter;
import in.buka.app.ui.adapters.DetailProjectAdapter;

public class DetailProjectActivity extends AppCompatActivity {

    protected RecyclerView projectRecyclerView;
    private Project project;
    private User creator;
    private ArrayList<Product> products = new ArrayList<>();

    public static String TAG = "BUKAIN";
    private RelativeLayout root;
    private ProgressDialog progress;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(R.string.app_name);
        }

        root = (RelativeLayout) findViewById(R.id.container_detail_project);

        projectRecyclerView = (RecyclerView) findViewById(R.id.project_recycler_view);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString(ActivityFeedAdapter.KEY_ID);
        Project.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                project = dataSnapshot.getValue(Project.class);
                progress = ProgressDialog.show(DetailProjectActivity.this, "", "Loading...", true, false);
                Log.d(TAG, dataSnapshot.toString());
                getProducts();
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
        String q = "";
        try {
            q = URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            q += "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(project.uid), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, Constants.PRODUCTS_URL + "?" + q);
        send.putString(BLService.KEY_URL, Constants.PRODUCTS_URL + "?" + q);
        send.putString(BLService.KEY_DATA, "");
        send.putString(BLService.KEY_TYPE, BLService.TYPE_AUTH);
        send.putString(BLService.KEY_REQUEST, HttpUtils.GET_REQUEST);
        service.putExtras(send);
        startService(service);
    }

    private void getUser() {
        Intent service = new Intent(this, BLService.class);
        Bundle send = new Bundle();

        send.putString(BLService.KEY_URL, "users/" + project.uid + "/profile.json");
        send.putString(BLService.KEY_DATA, "");
        send.putString(BLService.KEY_TYPE, BLService.TYPE_AUTH);
        send.putString(BLService.KEY_REQUEST, HttpUtils.GET_REQUEST);
        service.putExtras(send);
        startService(service);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle recv = intent.getExtras();

                JSONObject response = new JSONObject(recv.getString(BLService.KEY_RESPONSE));

                if (recv.getString(BLService.KEY_URL).split(Constants.PRODUCTS_URL).length == 2) {
                    if(response.getString("status").equals("OK")){
                        JSONArray prodcts = response.getJSONArray("products");
                        Log.d(TAG, prodcts.toString());
                        for(int a = 0; a < prodcts.length(); a++){
                            products.add(new Product(prodcts.getJSONObject(a)));
                        }
                        getUser();
                    } else if(response.getString("status").equals("ERROR")){
                        Snackbar.make(root, response.getString("message"), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Log.d(TAG, response.toString());
                    creator = JsonUtils.parseUserProfile(response);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (products != null && creator != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        projectRecyclerView = (RecyclerView) findViewById(R.id.project_recycler_view);
                        DetailProjectAdapter adapter = new DetailProjectAdapter(DetailProjectActivity.this, project, creator, products);
                        projectRecyclerView.setAdapter(adapter);
                        projectRecyclerView.setLayoutManager(new LinearLayoutManager(DetailProjectActivity.this));
                    }
                });
                progress.dismiss();
            }
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
