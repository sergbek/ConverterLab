package com.example.sergbek.converterlab;


import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.sergbek.converterlab.database.DatabaseController;
import com.example.sergbek.converterlab.database.DatabaseHelper;
import com.example.sergbek.converterlab.jsonLogic.JsonLoader;
import com.example.sergbek.converterlab.model.RootModelOrganization;
import com.example.sergbek.converterlab.utils.Utils;

public class ControlLogic {
    private Context mContext;
    private JsonLoader mJsonLoader;
    private DatabaseHelper mDatabaseHelper;
    private DatabaseController mDatabaseController;

    public static final String TAG = ControlLogic.class.getSimpleName();

    public ControlLogic(Context context) {
        mContext = context;
    }

    public void mainLogic() {
        initObject();
        if (Utils.isStateInternet(mContext)) {
            Log.d(TAG, "internet exist");
            displayMessage();
            RootModelOrganization rootModelOrganization = mJsonLoader.getRoot();
            if (Utils.doesDatabaseExist(mContext, DatabaseHelper.DATABASE_NAME)) {
                Log.d(TAG, "Database Exist");
                if (mDatabaseController.isUpdateDataBase(rootModelOrganization.getDate(), mDatabaseController.getDate())) {
                    mDatabaseController.saveRootModelInDB(rootModelOrganization);
                    Log.d(TAG, "date change");
                } else
                    Log.d(TAG, "date not change");
            } else {
                Log.d(TAG, "Database not Exist");
                mDatabaseController.saveRootModelInDB(rootModelOrganization);
            }
        } else {
            Log.d(TAG, "internet not exist");
        }
    }

    private void initObject() {
        mJsonLoader = new JsonLoader(mContext);
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseController = new DatabaseController(mDatabaseHelper);
    }


    public void displayMessage() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Начало загрузки данных")
                .setContentText("Загрузка данных");
        builder.setOngoing(true);

        Notification notification = builder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(1, notification);
    }


}
