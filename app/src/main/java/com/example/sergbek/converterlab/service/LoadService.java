package com.example.sergbek.converterlab.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.sergbek.converterlab.ControlLogic;


public class LoadService extends IntentService {

    public static final String ACTION_COMPLETE = "com.example.sergbek.converterlab.action.COMPLETE";

    public LoadService() {
        super("Download");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        ControlLogic controlLogic = new ControlLogic(this);
        controlLogic.mainLogic();
        sendBroadcast(new Intent(ACTION_COMPLETE));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
    }







}
