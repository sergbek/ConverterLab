package com.example.sergbek.converterlab.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.database.DatabaseController;
import com.example.sergbek.converterlab.database.DatabaseHelper;
import com.example.sergbek.converterlab.model.CurrencyModel;
import com.example.sergbek.converterlab.model.Organization;
import com.example.sergbek.converterlab.service.LoadService;
import com.example.sergbek.converterlab.utils.Utils;
import com.example.sergbek.converterlab.view.CurrencyView;
import com.example.sergbek.converterlab.view.DetailOrganizationView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        FloatingActionsMenu.OnFloatingActionsMenuUpdateListener,
        View.OnClickListener {

    private Toolbar mToolbar;
    private Organization mOrganization;
    List<CurrencyModel> mCurrencyModelList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FrameLayout mFrameLayout;
    private FloatingActionsMenu mFloatingActionsMenu;
    private DetailOrganizationView mDetailOrganizationView;
    private FloatingActionButton mPhone;
    private FloatingActionButton mSite;
    private FloatingActionButton mMap;
    private LinearLayout mLinearLayout;

    private static boolean is_Expanded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findView();

        String id = getIntent().getStringExtra("id");
        getOrganization(id);
        mDetailOrganizationView.setText(mOrganization);
        mCurrencyModelList = mOrganization.getCurrencies().getCurrencyModels();
        setCurrency();
        settingToolbar();
        settingPullDownRefresh();

        if (is_Expanded)
            mFrameLayout.setVisibility(View.VISIBLE);

        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Intent intent = new Intent(this, LoadService.class);
        startService(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(DetailActivity.this);
                notificationManager.cancel(1);
            }
        }, 2000);
    }

    @Override
    public void onMenuExpanded() {
        is_Expanded = true;
        mFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMenuCollapsed() {
        is_Expanded = false;
        mFrameLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_AD:
                Utils.callPhone(this, mOrganization);
                break;
            case R.id.map_AD:
                Utils.callMapActivity(this, mOrganization);
                break;
            case R.id.site_AD:
                Utils.openPageInternet(this, mOrganization);
                break;
        }
    }

    private void findView() {
        mDetailOrganizationView = (DetailOrganizationView) findViewById(R.id.DetailOrganizationView);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        mPhone = (FloatingActionButton) findViewById(R.id.phone_AD);
        mMap = (FloatingActionButton) findViewById(R.id.map_AD);
        mSite = (FloatingActionButton) findViewById(R.id.site_AD);
        mFrameLayout = (FrameLayout) findViewById(R.id.flVisibility);

    }

    private void setListeners() {
        mMap.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mSite.setOnClickListener(this);
        mFloatingActionsMenu.setOnFloatingActionsMenuUpdateListener(this);

    }

    private void settingPullDownRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void settingToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow);
        mToolbar.setTitle(mOrganization.getTitle());
        mToolbar.setSubtitle(mOrganization.getCityId());
        setSupportActionBar(mToolbar);
    }

    private void setCurrency() {
        for (int i = 0; i < mCurrencyModelList.size(); i++) {
            CurrencyView currencyView = new CurrencyView(this);

            currencyView.setText(mCurrencyModelList.get(i));
            mLinearLayout.addView(currencyView);
        }
    }

    private void getOrganization(String id) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DatabaseController databaseController = new DatabaseController(databaseHelper);
        mOrganization = databaseController.getOrganization(id);
    }
}
