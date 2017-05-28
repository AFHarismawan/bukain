/**
 * Halaman pertama kali buka APP:
 * List Project dengan tabulasi (Baru, Terdanai, Hampir Berakhir dll) dan tombol search
 **/

package in.buka.app.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.buka.app.R;
import in.buka.app.libs.configs.Constants;
import in.buka.app.models.Project;
import in.buka.app.ui.adapters.ActivityFeedAdapter;

/**
 * Created by A. Fauzi Harismawan on 5/6/2017.
 */

public class ActivityFeedActivity extends ActivityWithDrawer {

    private RecyclerView recyclerView;
    private ArrayList<Project> projects = new ArrayList<>();
    private MenuItem searchMenuItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        setContent(R.layout.activity_feed_layout);

        final ProgressDialog progress = ProgressDialog.show(this, "", "Loading...", true, false);
        Project.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(Constants.TAG, Long.toString(dataSnapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Project project = postSnapshot.getValue(Project.class);
                    project.id = postSnapshot.getKey();
                    Log.d(Constants.TAG, project.id);
                    projects.add(project);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        ActivityFeedAdapter adapter = new ActivityFeedAdapter(ActivityFeedActivity.this, projects);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFeedActivity.this));
                        Log.d(Constants.TAG, Integer.toString(projects.size()));
                        progress.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}