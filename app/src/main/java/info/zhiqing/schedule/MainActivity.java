package info.zhiqing.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import info.zhiqing.schedule.ui.FetchrActivity;
import info.zhiqing.schedule.ui.FriendsFragment;
import info.zhiqing.schedule.ui.InfoFragment;
import info.zhiqing.schedule.ui.ScheduleDayFragment;
import info.zhiqing.schedule.ui.ScoreListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SHARED_PREFER = "shared";
    public static final String SHARED_PREFER_NAME = "name";
    public static final String SHARED_PREFER_NUMBER = "number";
    public static final String SHARED_PREFER_COLLEGE = "college";
    public static final String SHARED_PREFER_MAJOR = "major";
    public static final String SHARED_PREFER_SEX = "sex";
    public static final String SHARED_PREFER_PROSPECT = "prospect";
    public static final String SHARED_PREFER_POLITICAL = "political";
    public static final String SHARED_PREFER_FROM = "from";
    public static final String SHARED_PREFER_MIDDLE_SCHOOL = "middle_school";
    public static final String SHARED_PREFER_NATION = "nation";
    public static final String SHARED_PREFER_ID = "id";
    public static final String SHARED_PREFER_QUALIFICATION = "qualification";
    public static final String SHARED_PREFER_CLASS = "class";


    private FetchrBroadcastReceiver receiver;

    TextView navHeaderNameTextView;
    TextView navHeaderCollegeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(R.string.nav_schedule);
        replaceFragment(new ScheduleDayFragment());


        IntentFilter filter = new IntentFilter();
        filter.addAction(FetchrActivity.BROADCAST);
        receiver = new FetchrBroadcastReceiver();
        registerReceiver(receiver, filter);

        navHeaderNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navHeaderNameTextView);
        navHeaderCollegeTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navHeaderCollegeTextView);
        update();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fetchr) {
            Intent intent = new Intent(this, FetchrActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            getSupportActionBar().setTitle(R.string.nav_schedule);
            replaceFragment(new ScheduleDayFragment());
        } else if (id == R.id.nav_score) {
            getSupportActionBar().setTitle(R.string.nav_score);
            replaceFragment(new ScoreListFragment());
        } else if (id == R.id.nav_info) {
            getSupportActionBar().setTitle(R.string.nav_info);
            replaceFragment(new InfoFragment());
        } else if (id == R.id.nav_friends) {
            getSupportActionBar().setTitle(R.string.nav_friends);
            replaceFragment(new FriendsFragment());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(fragment != null) {
            ft.replace(R.id.fragment, fragment);
        }
        ft.commit();
    }

    class FetchrBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            update();
        }
    }

    void update(){
        navHeaderNameTextView.setText(
                getApplication().getSharedPreferences(SHARED_PREFER, MODE_PRIVATE)
                        .getString(SHARED_PREFER_NAME, "姓名")
        );

        navHeaderCollegeTextView.setText(
                getApplication().getSharedPreferences(SHARED_PREFER, MODE_PRIVATE)
                        .getString(SHARED_PREFER_COLLEGE, "学院")
        );
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
