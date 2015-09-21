package com.example.sergbek.converterlab.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.adapter.OrganizationAdapter;
import com.example.sergbek.converterlab.broadcast.AlarmReceiver;
import com.example.sergbek.converterlab.database.DatabaseController;
import com.example.sergbek.converterlab.database.DatabaseHelper;
import com.example.sergbek.converterlab.model.Organization;
import com.example.sergbek.converterlab.service.LoadService;
import com.example.sergbek.converterlab.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private CompleteReceiver mCompleteReceiver;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Toolbar mToolbar;
    private List<Organization> mOrganizations;
    private OrganizationAdapter mOrganizationAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseHelper mDatabaseHelper;
    private DatabaseController mDatabaseController;

    public static final long INTERVAL = 30 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setSupportActionBar(mToolbar);
        settingPullDownRefresh();
        mDatabaseHelper = new DatabaseHelper(MainActivity.this);
        mDatabaseController = new DatabaseController(mDatabaseHelper);

        if (savedInstanceState == null) {
            mOrganizations = new ArrayList<>();
            mOrganizationAdapter = new OrganizationAdapter(mOrganizations);

            Intent intent = new Intent(this, LoadService.class);
            startService(intent);
            startAlarmManager();
        } else {
            if (Utils.doesDatabaseExist(this, DatabaseHelper.DATABASE_NAME)) {
                mOrganizations = mDatabaseController.getAllOrganizations();
                mOrganizationAdapter = new OrganizationAdapter(mOrganizations);
            }
        }

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mOrganizationAdapter);


        mCompleteReceiver = new CompleteReceiver();
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(
                LoadService.ACTION_COMPLETE);
        this.registerReceiver(mCompleteReceiver, filter);
    }

    @Override
    public void onPause() {
        this.unregisterReceiver(mCompleteReceiver);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView search =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setQueryHint("Enter your name");

        search.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mOrganizationAdapter.getFilter().filter("");
        } else {
            mOrganizationAdapter.getFilter().filter(newText);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        Intent intent = new Intent(MainActivity.this, LoadService.class);
        startService(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void startAlarmManager() {
        Intent alarm = new Intent(this, AlarmReceiver.class);

        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), INTERVAL, pendingIntent);
        }
    }

    private void settingPullDownRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ControlLogic", "done");


            mOrganizationAdapter = new OrganizationAdapter(mDatabaseController.getAllOrganizations());
            mRecyclerView.setAdapter(mOrganizationAdapter);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
            notificationManager.cancel(1);
        }
    }
}




