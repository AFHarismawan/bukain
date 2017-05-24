/**
 *  Halaman pertama kali buka APP:
 *  List Project dengan tabulasi (Baru, Terdanai, Hampir Berakhir dll) dan tombol search
 **/

package in.buka.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.FrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.buka.app.R;
import in.buka.app.models.Project;
import in.buka.app.ui.adapters.ActivityFeedAdapter;

/**
 * Created by A. Fauzi Harismawan on 5/6/2017.
 */

public class ActivityFeedActivity extends ActivityWithDrawer {

    private RecyclerView recyclerView;
    private ArrayList<Project> projects = new ArrayList<>();

    public static String TAG = "BUKAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        setContent(R.layout.activity_feed_layout);

        Project.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, Long.toString(dataSnapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Project project = postSnapshot.getValue(Project.class);
                    project.id = dataSnapshot.getKey();
                    projects.add(project);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    ActivityFeedAdapter adapter = new ActivityFeedAdapter(ActivityFeedActivity.this, projects);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFeedActivity.this));
                    Log.d(TAG, Integer.toString(projects.size()));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}