package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BusinessEditProfile extends ActionBarActivity {

    private EditText editAddress;
    private EditText editDesc;
    private Button btnUpdate;
    private String _businessName;
    private IBL bl;
    private double latitude;
    private double longitude;
    private List<Address> geocodeMatches = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_edit_profile);
        initialize();
    }

    private void initialize() {
        editAddress = (EditText) findViewById(R.id.businessEdit_address);
        editDesc = (EditText) findViewById(R.id.businessEdit_desc);
        btnUpdate = (Button) findViewById(R.id.businessEdit_btnUpdate);
        bl = new BL();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _businessName = extras.getString("businessName");
        }
        ResultSet businessDetails = bl.getBusinessByName(_businessName);
        try {
            businessDetails.next();
            editAddress.setText(businessDetails.getString(2));
            editDesc.setText(businessDetails.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onClickEditProfile(View v){

        String newAddress = editAddress.getText().toString();
        String newDesc = editDesc.getText().toString();
        if((!"".equals(newAddress)) && (!"".equals(newDesc))){
            try
            {
                geocodeMatches = new Geocoder(this).getFromLocationName(newAddress, 1);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (!geocodeMatches.isEmpty())
            {
                longitude = geocodeMatches.get(0).getLongitude();
                latitude = geocodeMatches.get(0).getLatitude();
            }
            bl.updateBusinessProfile(_businessName, newAddress, newDesc, longitude, latitude);
            Toast.makeText(this, "Business was Updated!", Toast.LENGTH_LONG).show();

        }

    }


}
