package in.buka.app.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import in.buka.app.R;
import in.buka.app.models.Project;

public class DetailProjectActivity extends AppCompatActivity {

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

        projectRecyclerView = (RecyclerView) findViewById(R.id.project_recycler_view);
    }
}