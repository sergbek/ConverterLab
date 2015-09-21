package com.example.sergbek.converterlab.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.activity.DetailActivity;
import com.example.sergbek.converterlab.model.Organization;
import com.example.sergbek.converterlab.utils.Utils;


public class OrganizationView extends LinearLayout implements TabLayout.OnTabSelectedListener {
    private TextView mTvTitle;
    private TextView mTvRegion;
    private TextView mTvCity;
    private TextView mTvPhone;
    private TabLayout mTabLayout;
    private TextView mTvAddress;
    private Context mContext;
    private Organization mOrganization;

    private int[] mImageResId = {R.drawable.ic_link, R.drawable.ic_map,
            R.drawable.ic_phone, R.drawable.ic_next};


    public OrganizationView(Context context) {
        super(context);
        this.mContext = context;
        init();
        mTabLayout.setOnTabSelectedListener(this);
    }

    public OrganizationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
        mTabLayout.setOnTabSelectedListener(this);
    }

    public OrganizationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        mTabLayout.setOnTabSelectedListener(this);
    }

    private void init() {
        inflate(getContext(), R.layout.view_organization, this);
        mTvTitle = (TextView) findViewById(R.id.tv_title_VO);
        mTvRegion = (TextView) findViewById(R.id.tv_region_VO);
        mTvCity = (TextView) findViewById(R.id.tv_city_VO);
        mTvPhone = (TextView) findViewById(R.id.tv_phone_VO);
        mTvAddress = (TextView) findViewById(R.id.tv_address_VO);


        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for (int i = 0; i < 4; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setIcon(mImageResId[i]));
        }
    }

    public Organization getOrganization() {
        return mOrganization;
    }

    public void setOrganization(Organization organization) {
        mOrganization = organization;
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public TextView getTvRegion() {
        return mTvRegion;
    }

    public TextView getTvCity() {
        return mTvCity;
    }

    public TextView getTvPhone() {
        return mTvPhone;
    }

    public TextView getTvAddress() {
        return mTvAddress;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                Utils.openPageInternet(mContext, mOrganization);
                break;
            case 1:
                Utils.callMapActivity(mContext, mOrganization);
                break;
            case 2:
                Utils.callPhone(mContext, mOrganization);
                break;
            case 3:
                nextDetailPage();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                Utils.openPageInternet(mContext, mOrganization);
                break;
            case 1:
                Utils.callMapActivity(mContext, mOrganization);
                break;
            case 2:
                Utils.callPhone(mContext, mOrganization);
                break;
            case 3:
                nextDetailPage();
                break;
        }
    }

    private void nextDetailPage() {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("id", mOrganization.getId());
        mContext.startActivity(intent);
    }

}
