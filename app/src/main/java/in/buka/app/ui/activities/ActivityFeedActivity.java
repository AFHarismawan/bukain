package in.buka.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.buka.app.R;
import in.buka.app.models.Project;
import in.buka.app.ui.adapters.ActivityFeedAdapter;

/**
 * Created by A. Fauzi Harismawan on 5/6/2017.
 */

public class ActivityFeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private ArrayList<Project> projects = new ArrayList<>();

    public static String TAG = "BUKAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_feed_swipe_refresh_layout);

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("projects");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, Long.toString(dataSnapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    projects.add(postSnapshot.getValue(Project.class));
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_feed_logged_drawer);
        navigationView.setCheckedItem(R.id.nav_all_project);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent change;
        switch (id){
            case R.id.nav_login:
                change = new Intent(this, ToutActivity.class);
                startActivity(change);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
