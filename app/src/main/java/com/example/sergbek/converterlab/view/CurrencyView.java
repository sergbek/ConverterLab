package com.example.sergbek.converterlab.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.model.CurrencyModel;


public class CurrencyView extends LinearLayout {

    private TextView mCurrencyName;
    private TextView mCurrencyTextAsk;
    private TextView mCurrencyTextBit;
    private ImageView mCurrencyImageAsk;
    private ImageView mCurrencyImageBit;

    private static final String COLOR_ARROW_UP = "#05616A";
    private static final String COLOR_ARROW_DOWN = "#890E4F";

    public CurrencyView(Context context) {
        super(context);
        init();
    }

    public CurrencyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_currency, this);
        mCurrencyName = (TextView) findViewById(R.id.tv_currencyTitle_AC);
        mCurrencyTextAsk = (TextView) findViewById(R.id.tv_currencyASK_AC);
        mCurrencyTextBit = (TextView) findViewById(R.id.tv_currencyBIT_AC);
        mCurrencyImageAsk = (ImageView) findViewById(R.id.iv_currencyASK_AC);
        mCurrencyImageBit = (ImageView) findViewById(R.id.iv_currencyBIT_AC);
    }

    public void setText(CurrencyModel currencyModel) {
        mCurrencyName.setText(currencyModel.getFullTitle());
        mCurrencyTextAsk.setText(currencyModel.getAsk());
        mCurrencyTextBit.setText(currencyModel.getBid());
        if (currencyModel.getChangeAsk() == 1) {
            mCurrencyImageAsk.setImageResource(R.drawable.ic_green_arrow_up);
            mCurrencyTextAsk.setTextColor(Color.parseColor(COLOR_ARROW_UP));
        } else {
            mCurrencyImageAsk.setImageResource(R.drawable.ic_red_arrow_down);
            mCurrencyTextAsk.setTextColor(Color.parseColor(COLOR_ARROW_DOWN));
        }

        if (currencyModel.getChangeBit() == 1) {
            mCurrencyImageBit.setImageResource(R.drawable.ic_green_arrow_up);
            mCurrencyTextBit.setTextColor(Color.parseColor(COLOR_ARROW_UP));
        } else {
            mCurrencyImageBit.setImageResource(R.drawable.ic_red_arrow_down);
            mCurrencyTextBit.setTextColor(Color.parseColor(COLOR_ARROW_DOWN));
        }
    }
}
