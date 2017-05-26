package in.buka.app.ui.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import in.buka.app.R;
import in.buka.app.libs.database.DatabaseHelper;

/**
 * Created by Shade on 5/24/17.
 */

class ActivityWithDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper helper;

    protected void setContent(int resource) {
        FrameLayout container = (FrameLayout) findViewById(R.id.main_container);
        View view = getLayoutInflater().inflate(resource, null, false);
        container.addView(view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        helper = new DatabaseHelper(this);
        if (helper.getCredentials().name != null) {
            Log.d("TOKEN", helper.getCredentials().token);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_feed_logged_drawer);
            Menu menu = navigationView.getMenu();
            MenuItem item = menu.findItem(R.id.nav_login);
            item.setTitle(helper.getCredentials().name);

            navigationView.setCheckedItem(R.id.nav_all_project);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_feed_drawer);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent change;
        switch (id) {
            case R.id.nav_login:
                if (helper.getCredentials().name != null) {
                    change = new Intent(this, ProfileActivity.class);
                    startActivity(change);
                } else {
                    change = new Intent(this, ToutActivity.class);
                    startActivity(change);
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
