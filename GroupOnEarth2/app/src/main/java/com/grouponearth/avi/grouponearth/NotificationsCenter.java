package com.grouponearth.avi.grouponearth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.PresentationLayer.ClientMenu;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Avi on 14/05/2015.
 */
public class NotificationsCenter{

    private IBL bl;
    private ResultSet rs;
    private double _long;
    private double _lat;
    private String _userName;
    private NotificationManager notificationManager;
    private Activity activity;

    public NotificationsCenter(double longitude, double lat, String userName, Activity activity){
        _long = longitude;
        _lat = lat;
        _userName = userName;
        bl = new BL();
        this.activity = activity;
        //nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

    }

    public void checkNotifications(){
        rs = bl.getCouponsByDistance(_long,_lat,400);
        try {
            rs.last();
            if(rs.getRow()>1){
                showAlert(this.activity,"There Are Coupons Around You");
            }
        }
        catch(SQLException e){

        }
        if(bl.existUserCloseExpCoupons(_userName)){
            showAlert(this.activity, "Some Coupons Are Close To Expire!");
        }
    }




    private static void showAlert(Activity activity, String message) {

        TextView title = new TextView(activity);
        title.setText("Coupon Alert");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Alert!");
        builder.setCustomTitle(title);
        builder.setIcon(R.drawable.background);

        builder.setMessage(message);

        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }

        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
