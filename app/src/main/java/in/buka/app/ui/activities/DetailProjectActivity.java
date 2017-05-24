/**
 *  Halaman pertama kali buka APP:
 *  List Project dengan tabulasi (Baru, Terdanai, Hampir Berakhir dll) dan tombol search
 **/

package in.buka.app.ui.activities;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import in.buka.app.R;
import in.buka.app.models.Project;
import in.buka.app.ui.adapters.ActivityFeedAdapter;
import in.buka.app.ui.viewholders.ProjectViewHolder;

public class DetailProjectActivity extends AppCompatActivity {

    protected RecyclerView projectRecyclerView;
    private Project project;

    public static String TAG = "BUKAIN";
    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        root = (RelativeLayout) findViewById(R.id.container_detail_project);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_feed_swipe_refresh_layout);
        projectRecyclerView = (RecyclerView) findViewById(R.id.project_recycler_view);

        Bundle bundle = getIntent().getExtras();
        Project.get(bundle.getString(ActivityFeedAdapter.KEY_ID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                project = dataSnapshot.getValue(Project.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(root, databaseError.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }
}
