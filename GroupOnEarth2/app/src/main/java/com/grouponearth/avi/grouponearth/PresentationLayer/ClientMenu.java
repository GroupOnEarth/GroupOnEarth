package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.NotificationsCenter;
import com.grouponearth.avi.grouponearth.R;
public class ClientMenu extends ActionBarActivity implements LocationListener {
    private String _userName;
    private TextView header;
    private IBL bl;
    private LocationManager locationManager;
    private Location location;
    private double longitude;
    private double latitude;
    private boolean isGPSEnabled= false;
    private boolean isNetworkEnabled= false;
    private boolean canGetLocation= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu);
        bl = new BL();
        header = (TextView)findViewById(R.id.txtHeader);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _userName = extras.getString("userName");
            header.setText("Hello "+_userName);
        }
        locationManager= (LocationManager) (ClientMenu.this).getSystemService(LOCATION_SERVICE);
        isGPSEnabled= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isGPSEnabled)
        {
            showSettingAlert();
        }

            this.canGetLocation= true;
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                }
            }
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                }
            }
            bl.updateClientLocation(_userName,longitude,latitude);
            if(longitude!=0 & latitude!=0){
                NotificationsCenter nc = new NotificationsCenter(longitude,latitude,_userName, this);
                nc.checkNotifications();
            }

    }
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogout:
                onClickLogout();
                break;
            case R.id.btnSearch:
                onClickSearch();
                break;
            case R.id.btnAround:
                onClickAroundMe();
                break;
            case R.id.btnMyCoupons:
                onClickMyCoupons();
                break;
        }
    }
    public void onClickSearch()
    {
        Intent intent = new Intent(this, SearchCoupons.class);
        intent.putExtra("userName", _userName);
        startActivity(intent);
    }
    public void onClickMyCoupons()
    {
        Intent intent = new Intent(this, ClientCoupons.class);
        intent.putExtra("userName", _userName);

        startActivity(intent);
    }
    public void onClickAroundMe()
    {
        Intent intent = new Intent(this, AroundMeCouponsPage.class);
        intent.putExtra("userName", _userName);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        startActivity(intent);
    }
    public void onClickLogout(){
        this.onBackPressed();
    }
    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
    public void showSettingAlert(){
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(ClientMenu.this);
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to setting menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    public void onResume(){
        super.onResume();
        locationManager= (LocationManager) (ClientMenu.this).getSystemService(LOCATION_SERVICE);
        isGPSEnabled= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            this.canGetLocation= true;
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                }
            }
            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                }
            }
            bl.updateClientLocation(_userName,longitude,latitude);

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
