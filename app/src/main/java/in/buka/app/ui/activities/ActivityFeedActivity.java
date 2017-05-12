package in.buka.app.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import in.buka.app.R;

/**
 * Created by A. Fauzi Harismawan on 5/6/2017.
 */

public class ActivityFeedActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_layout);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_feed_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


    }
}
