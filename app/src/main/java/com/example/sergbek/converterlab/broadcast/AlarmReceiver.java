package com.example.sergbek.converterlab.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.sergbek.converterlab.activity.MainActivity;
import com.example.sergbek.converterlab.service.LoadService;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        startAlarmManager(context);
        context.startService(new Intent(context, LoadService.class));
    }

    private void startAlarmManager(Context context) {
        Intent alarm = new Intent(context, AlarmReceiver.class);

        boolean alarmRunning = (PendingIntent.getBroadcast(context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), MainActivity.INTERVAL, pendingIntent);
        }
    }
}
