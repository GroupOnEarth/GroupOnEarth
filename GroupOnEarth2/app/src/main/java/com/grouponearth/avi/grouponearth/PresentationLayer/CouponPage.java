package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class CouponPage extends ActionBarActivity {
    private IBL bl;
    private String _couponID;
    private String _userName;
    private TextView txtBName;
    private TextView txtDesc;
    private TextView txtPrice;
    private TextView txtDPrice;
    private TextView txtAddress;
    private TextView txtBought;
    private TextView txtHeader;
    private RatingBar ratingBar;
    private int _alreadyBought;
    private TextView expDate;
    private Button btnBuy;
    private Boolean boughtCoupon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePage();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _couponID = extras.getString("couponID");
            _userName = extras.getString("userName");
        }
        ResultSet coupon = bl.getCouponByID(_couponID);
        try {
            coupon.next();
            setCouponToViews(coupon);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void boughtMsg(){
        Toast.makeText(this, "Congratulations!\nPurchase Was Made", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v){
            _alreadyBought = bl.getAmountOfPurchasedCoupons(_couponID);
            new AlertDialog.Builder(this)
                    .setTitle("Buy Coupon")
                    .setMessage("Are you sure you want buy this coupon?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bl.addPurchase(_userName, _couponID, _alreadyBought);
                            _alreadyBought = bl.getAmountOfPurchasedCoupons(_couponID);
                            txtBought.setText("Already Bought: " + _alreadyBought);
                            boughtMsg();

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();



        }



    private void initializePage(){
        boughtCoupon = false;
        setContentView(R.layout.coupon_page);
        bl = new BL();
        txtBName = (TextView) findViewById(R.id.txtBName);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtDPrice = (TextView) findViewById(R.id.txtDPrice);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        txtBought = (TextView) findViewById(R.id.txtBought);
        expDate = (TextView) findViewById(R.id.txtExpDate);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

    }

    private void setCouponToViews(ResultSet coupon){
        try {
            txtHeader.setText(coupon.getString(2));
            txtDesc.setText(txtDesc.getText().toString()+coupon.getString(3));
            txtPrice.setText(txtPrice.getText().toString()+coupon.getString(5));
            txtDPrice.setText(txtDPrice.getText().toString()+coupon.getString(6));
            txtBName.setText(txtBName.getText().toString()+coupon.getString(8));
            txtAddress.setText(txtAddress.getText().toString()+coupon.getString(9));
            ratingBar.setRating((float)coupon.getDouble(11));
            try {
                expDate.setText(expDate.getText().toString() + coupon.getDate(7));
            }
            catch(Exception e){
                expDate.setText(expDate.getText().toString() + "None");
            }

            _alreadyBought = bl.getAmountOfPurchasedCoupons(_couponID);
            txtBought.setText(txtBought.getText().toString()+_alreadyBought);

        }
        catch (Exception e){
            Toast.makeText(this, "Could Not\nLoad Coupon", Toast.LENGTH_LONG);
        }

    }
}
