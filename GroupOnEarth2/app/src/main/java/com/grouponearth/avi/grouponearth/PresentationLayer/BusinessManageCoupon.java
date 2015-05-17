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

public class BusinessManageCoupon extends ActionBarActivity {

    private IBL bl;
    private String _couponID;
    private TextView txtDesc;
    private TextView txtPrice;
    private TextView txtDPrice;
    private TextView txtBought;
    private TextView txtHeader;
    private RatingBar ratingBar;
    private TextView txtFulfilled;
    private int _alreadyBought;
    private int _alreadyFulfilled;
    private TextView expDate;
    private Button btnDelete;
    private Button btnFulfilled;
    private TextView txtSerial;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_manage_coupon);
        initialize();
    }

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _couponID = extras.getString("couponID");
        }
        bl = new BL();
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtDPrice = (TextView) findViewById(R.id.txtDPrice);
        txtBought = (TextView) findViewById(R.id.txtBought);
        expDate = (TextView) findViewById(R.id.txtExpDate);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnDelete = (Button) findViewById(R.id.business_btnDelete);
        btnFulfilled = (Button) findViewById(R.id.business_btnfulFilled);
        txtFulfilled = (TextView) findViewById(R.id.txtFulfilled);
        txtSerial = (TextView) findViewById(R.id.txtSerial);


        ResultSet coupon = bl.getCouponByID(_couponID);
        try {
            coupon.next();
            setCouponToViews(coupon);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void onClick(View v){
        switch(v.getId()){
            case (R.id.business_btnDelete):
                onClickDeleteCoupon();
                break;
            case (R.id.business_btnfulFilled):
                onClickCouponFulfilled();
                break;
        }
    }

    private void onClickCouponFulfilled() {
        String serialNumber = txtSerial.getText().toString();
        if(!("".equals(serialNumber))){
            if(bl.isPurchaseFulfilled(serialNumber)) {
                Toast.makeText(this, "Coupon Already\nFulfilled", Toast.LENGTH_SHORT).show();

            }
            else{
                bl.fulfillCoupon(serialNumber);
                Toast.makeText(this, "Coupon Fulfilled", Toast.LENGTH_SHORT).show();
                _alreadyFulfilled += 1;
                txtSerial.setText("");
                txtFulfilled.setText("Fulfilled: " + _alreadyFulfilled);
            }
        }
    }

    private void onClickDeleteCoupon() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Coupon")
                .setMessage("Are you sure you want Delete this coupon?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bl.deleteCoupon(_couponID);
                        deletedMsg();
                        onBackPressed();

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
    public void deletedMsg(){
        Toast.makeText(this, "Coupon Deleted", Toast.LENGTH_SHORT).show();
    }


    private void setCouponToViews(ResultSet coupon){
        try {
            txtHeader.setText(coupon.getString(2));
            txtDesc.setText(txtDesc.getText().toString()+coupon.getString(3));
            txtPrice.setText(txtPrice.getText().toString()+coupon.getString(5));
            txtDPrice.setText(txtDPrice.getText().toString()+coupon.getString(6));
            ratingBar.setRating((float)coupon.getDouble(11));
            try {
                expDate.setText(expDate.getText().toString() + coupon.getDate(7));
            }
            catch(Exception e){
                expDate.setText(expDate.getText().toString() + "None");
            }

            _alreadyBought = bl.getAmountOfPurchasedCoupons(_couponID);
            txtBought.setText(txtBought.getText().toString()+_alreadyBought);
            _alreadyFulfilled = bl.getAmountOfFulfilledCoupons(_couponID);
            txtFulfilled.setText(txtFulfilled.getText().toString()+_alreadyFulfilled);
            txtSerial.clearFocus();

        }
        catch (Exception e){
            Toast.makeText(this, "Could Not\nLoad Coupon", Toast.LENGTH_LONG).show();
        }

    }


}
