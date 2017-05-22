package in.buka.app.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.buka.app.R;
import in.buka.app.models.Project;

/**
 * Created by A. Fauzi Harismawan on 5/6/2017.
 */

public class ActivityFeedActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;


    private String TAG = "BUKAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_layout);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_feed_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("projects");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        
                }
                Project project = dataSnapshot.child("0").getValue(Project.class);
                Log.d(TAG, project.name + " " + project.desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
