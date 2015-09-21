package com.example.sergbek.converterlab.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.model.Organization;
import com.example.sergbek.converterlab.utils.Utils;


public class DetailOrganizationView extends RelativeLayout {
    private TextView mTvTitle;
    private TextView mTvInternetAddress;
    private TextView mTvAddress;
    private TextView mTvPhone;
    private TextView mTvPhoneText;
    private TextView mTvTypeOrg;
    private TextView mTvCity;
    private TextView mTvRegion;
    private TextView mTvTextRegion;

    public DetailOrganizationView(Context context) {
        super(context);
        init();
    }


    public DetailOrganizationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailOrganizationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.view_detail_organization, this);
        mTvTitle = (TextView) findViewById(R.id.tv_title_VDO);
        mTvInternetAddress = (TextView) findViewById(R.id.tv_inetAddress_VDO);
        mTvAddress = (TextView) findViewById(R.id.tv_address_VDO);
        mTvPhone = (TextView) findViewById(R.id.tv_phone_VDO);
        mTvPhoneText = (TextView) findViewById(R.id.tv_phoneText_VDO);
        mTvTypeOrg = (TextView) findViewById(R.id.tv_type_org_VDO);
        mTvCity = (TextView) findViewById(R.id.tv_city_VDO);
        mTvRegion = (TextView) findViewById(R.id.tv_region_VDO);
        mTvTextRegion = (TextView) findViewById(R.id.tv_textRegion_VDO);
    }

    public void setText(Organization organization) {
        mTvTitle.setText(organization.getTitle());
        mTvInternetAddress.setPaintFlags(mTvInternetAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mTvInternetAddress.setText(organization.getLink());
        mTvAddress.setText(organization.getAddress());
        if (organization.getPhone() != null) {
            mTvPhone.setText(Utils.formatPhoneNumber(organization.getPhone()));
        } else {
            mTvPhoneText.setVisibility(GONE);
            mTvPhone.setVisibility(GONE);
        }
        mTvTypeOrg.setText(Utils.formatTypeOrganization(organization.getOrgType()));
        if (organization.getRegionId().equalsIgnoreCase(organization.getCityId())) {
            mTvCity.setText(organization.getCityId());
            mTvTextRegion.setVisibility(GONE);
            mTvRegion.setVisibility(GONE);
        } else {
            mTvRegion.setText(organization.getRegionId());
            mTvCity.setText(organization.getCityId());
        }

    }

}
