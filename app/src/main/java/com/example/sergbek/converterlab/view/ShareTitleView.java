package com.example.sergbek.converterlab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.model.Organization;


public class ShareTitleView extends LinearLayout {

    private TextView mTxtTitleName;
    private TextView mTxtRegion;
    private TextView mTxtCity;

    public ShareTitleView(Context context) {
        super(context);
        init();
    }

    public ShareTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShareTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_share_title, this);

        mTxtTitleName = (TextView) findViewById(R.id.tvTitle_VST);
        mTxtRegion = (TextView) findViewById(R.id.tvRegion_VST);
        mTxtCity = (TextView) findViewById(R.id.tvCity_VST);
    }

    public void setText(Organization organization) {

        mTxtTitleName.setText(organization.getTitle());
        mTxtRegion.setText(organization.getRegionId());
        mTxtCity.setText(organization.getCityId());
    }
}
