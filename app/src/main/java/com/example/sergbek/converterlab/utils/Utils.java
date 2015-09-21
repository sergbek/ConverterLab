package com.example.sergbek.converterlab.utils;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.example.sergbek.converterlab.activity.MapActivity;
import com.example.sergbek.converterlab.model.Organization;

import java.io.File;

public class Utils {

    public static String formatPhoneNumber(String phoneNum) {
        StringBuilder sb = new StringBuilder(15);
        StringBuilder temp = new StringBuilder(phoneNum);

        while (temp.length() < 10)
            temp.insert(0, "0");

        char[] chars = temp.toString().toCharArray();

        sb.append("(");

        for (int i = 0; i < chars.length; i++) {
            if (i == 3)
                sb.append(") ");
            else if (i == 6)
                sb.append("-");
            sb.append(chars[i]);
        }

        return sb.toString();
    }

    public static String formatTypeOrganization(String str){
        if (str.equalsIgnoreCase("Банки"))
            return str.substring(0,str.length()-1);

        return str;
    }

    public static boolean isStateInternet(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);

        return dbFile.exists();
    }

    public static void callPhone(Context context,Organization organization) {
        if (organization.getPhone() != null) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(String.format("tel:%s", organization.getPhone())));
            context.startActivity(intent);
        }
    }

    public static void openPageInternet(Context context,Organization organization) {
        Uri address = Uri.parse(organization.getLink());
        Intent openlink = new Intent(Intent.ACTION_VIEW, address);
        context.startActivity(openlink);
    }

    public static void callMapActivity(Context context,Organization organization){
        Intent intent=new Intent(context, MapActivity.class);
        intent.putExtra("title",organization.getTitle());
        intent.putExtra("city",organization.getCityId());
        intent.putExtra("address",organization.getAddress());
        context.startActivity(intent);
    }
}
