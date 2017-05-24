/**
 *  Halaman pertama kali buka APP:
 *  List Project dengan tabulasi (Baru, Terdanai, Hampir Berakhir dll) dan tombol search
 **/

package in.buka.app.ui.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import in.buka.app.R;
import in.buka.app.models.Project;

import java.util.ArrayList;

public class DetailProjectActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView projectRecyclerView;
    private Project project;

    public static String TAG = "BUKAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_feed_swipe_refresh_layout);
        projectRecyclerView = (RecyclerView) findViewById(R.id.project_recycler_view);
    }
}
