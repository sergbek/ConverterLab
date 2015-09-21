package com.example.sergbek.converterlab.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.model.CurrencyModel;

import java.math.BigDecimal;


public class ShareCurrencyView extends LinearLayout {

    private TextView mTvAbbr;
    private TextView mTvAskBid;

    public ShareCurrencyView(Context context) {
        super(context);
        init();
    }

    public ShareCurrencyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShareCurrencyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_share_currency, this);
        mTvAbbr = (TextView) findViewById(R.id.tv_abbr_VSC);
        mTvAskBid = (TextView) findViewById(R.id.tv_AskBid_VSC);
    }

    public void setText(CurrencyModel currencyModel){

        BigDecimal ask = new BigDecimal(currencyModel.getAsk());
        BigDecimal bid = new BigDecimal(currencyModel.getAsk());

        mTvAbbr.setText(currencyModel.getAbbr() + "      ");
        mTvAbbr.setTextColor(ContextCompat.getColor(getContext(), R.color.accentColor));

        mTvAskBid.setText("          "+ask.setScale(2, BigDecimal.ROUND_HALF_EVEN) + "/" +
                bid.setScale(2, BigDecimal.ROUND_HALF_EVEN)+"");
    }
}
