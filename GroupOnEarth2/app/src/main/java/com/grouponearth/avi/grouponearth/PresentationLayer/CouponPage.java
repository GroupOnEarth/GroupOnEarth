package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private int _alreadyBought;
    private Button btnBuy;


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coupon_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializePage(){
        setContentView(R.layout.coupon_page);
        bl = new BL();
        txtBName = (TextView) findViewById(R.id.txtBName);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtDPrice = (TextView) findViewById(R.id.txtDPrice);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        txtBought = (TextView) findViewById(R.id.txtBought);
    }

    private void setCouponToViews(ResultSet coupon){
        try {
            txtBName.setText(txtBName.getText().toString()+coupon.getString(9));
            txtDesc.setText(txtDesc.getText().toString()+coupon.getString(3));
            txtPrice.setText(txtPrice.getText().toString()+coupon.getString(5));
            txtDPrice.setText(txtDPrice.getText().toString()+coupon.getString(6));
            txtAddress.setText(txtAddress.getText().toString()+coupon.getString(2));
            _alreadyBought = bl.getAmountOfPurchasedCoupons(_couponID);
            txtBought.setText(txtBought.getText().toString()+_alreadyBought);
        }
        catch (SQLException e){

        }

    }
}
