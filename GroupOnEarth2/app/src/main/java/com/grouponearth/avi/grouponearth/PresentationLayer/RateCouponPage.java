package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RateCouponPage extends ActionBarActivity {
    private IBL bl;
    private String _couponID;
    private String _userName;
    private TextView txtBName;
    private TextView txtDesc;
    private TextView txtAddress;
    private TextView txtHeader;
    private RatingBar ratingBar;
    private TextView txtCurrRating;
    private Button btnRate;
    private double currentRating;
    private int numOfRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_coupon_page);
        initializePage();
        ResultSet coupon = bl.getCouponByID(_couponID);
        try {
            coupon.next();
            setCouponToViews(coupon);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializePage(){
        bl = new BL();
        txtBName = (TextView) findViewById(R.id.txtBName);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnRate = (Button) findViewById(R.id.btnRate);
        txtCurrRating = (TextView) findViewById(R.id.txtCurrRating);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _couponID = extras.getString("couponID");
            _userName = extras.getString("userName");
        }
        try {
            ResultSet ratingInfo = bl.getCouponRatingInfo(_couponID);
            ratingInfo.next();
            currentRating = ratingInfo.getDouble(1);
            numOfRating = ratingInfo.getInt(2);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCouponToViews(ResultSet coupon) {
        try {
            txtHeader.setText(coupon.getString(2));
            txtDesc.setText(txtDesc.getText().toString() + coupon.getString(3));
            txtBName.setText(txtBName.getText().toString() + coupon.getString(8));
            txtAddress.setText(txtAddress.getText().toString() + coupon.getString(9));
        } catch (Exception e) {
            Toast.makeText(this, "Could Not\nLoad Coupon", Toast.LENGTH_LONG);
        }
    }

    public void onClick(View v){
        double newRating = ratingBar.getRating();
        newRating = (currentRating*numOfRating) + newRating;
        newRating = newRating / (double)(numOfRating+1);
        ratingBar.setRating((float)newRating);
        txtCurrRating.setVisibility(View.VISIBLE);
        btnRate.setVisibility(View.GONE);
        bl.updateCouponRatingInfo(_couponID, newRating);
        onBackPressed();
    }
}
