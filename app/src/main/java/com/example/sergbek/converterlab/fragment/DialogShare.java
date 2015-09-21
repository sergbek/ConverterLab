package com.example.sergbek.converterlab.fragment;


import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.database.DatabaseController;
import com.example.sergbek.converterlab.database.DatabaseHelper;
import com.example.sergbek.converterlab.model.CurrencyModel;
import com.example.sergbek.converterlab.model.Organization;
import com.example.sergbek.converterlab.view.ShareCurrencyView;
import com.example.sergbek.converterlab.view.ShareTitleView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DialogShare extends DialogFragment implements View.OnClickListener {

    private ImageView mIvShare;
    private Button mBtnShare;

    private DatabaseController mDatabaseController;
    private ShareActionProvider mShareActionProvider;

    private LinearLayout mLinearLayout;
    private Organization mOrganization;
    private List<CurrencyModel> mCurrencyModelList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        removeTitleDialog();
        setAnimation();
        return inflater.inflate(R.layout.fragment_dialog_share, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findView();

        showCoursesByOrg();

        mBtnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendIntent();
        dismiss();
    }

    private void showCoursesByOrg() {
        mDatabaseController=new DatabaseController(new DatabaseHelper(getActivity()));


        mOrganization = mDatabaseController.getOrganization(getArguments().getString("id"));

        mLinearLayout = new LinearLayout(getActivity());
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);

        mCurrencyModelList = mOrganization.getCurrencies().getCurrencyModels();

        ShareTitleView shareTitleView = new ShareTitleView(getActivity());
        shareTitleView.setText(mOrganization);
        mLinearLayout.addView(shareTitleView);

        for (int i = 0; i < mCurrencyModelList.size(); i++) {
            ShareCurrencyView shareCurrencyView = new ShareCurrencyView(getActivity());
            shareCurrencyView.setText(mCurrencyModelList.get(i));
            mLinearLayout.addView(shareCurrencyView);
        }

        mIvShare.setImageBitmap(getBitmapFromView(mLinearLayout));

    }


    public Bitmap getBitmapFromView(View view) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        mLinearLayout.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.layout(0, 0, width, mLinearLayout.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }


    public File saveImage() {
        String folderToSave = Environment.getExternalStorageDirectory().toString();
        File file = new File(folderToSave, String.valueOf(mOrganization.getId().hashCode())+".jpg");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            Bitmap bitmap = getBitmapFromView(mLinearLayout);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                    file.getAbsolutePath(), file.getName(), file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void sendIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        File file = saveImage();
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/*");
        startActivity(intent);
    }

    private void findView() {
        mBtnShare = (Button) getView().findViewById(R.id.btn_share_FDS);
        mIvShare = (ImageView) getView().findViewById(R.id.iv_share_FDS);
    }

    private void setAnimation() {
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.CustomDialogAnim;
    }

    private void removeTitleDialog() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


}
