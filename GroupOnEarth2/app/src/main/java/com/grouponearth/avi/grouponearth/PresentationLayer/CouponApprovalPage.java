package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;
import java.sql.ResultSet;
import java.sql.SQLException;
public class CouponApprovalPage extends ActionBarActivity {
    private IBL bl;
    private String _couponID;
    private String businessName;
    private String _couponName;
    private TextView txtBName;
    private TextView txtDesc;
    private TextView txtPrice;
    private TextView txtDPrice;
    private TextView txtAddress;
    private TextView txtHeader;
    private TextView expDate;
    private Button btnApprove;
    private Button btnDeleteCoupon;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_approval_page);
        initializePage();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _couponID = extras.getString("couponID");
        }
        ResultSet coupon = bl.getCouponByID(_couponID);
        try {
            coupon.next();
            setCouponToViews(coupon);
            _couponName=coupon.getString(2);
            businessName= coupon.getString(8);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initializePage(){
        bl = new BL();
        txtBName = (TextView) findViewById(R.id.txtBName);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtDPrice = (TextView) findViewById(R.id.txtDPrice);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        btnApprove = (Button) findViewById(R.id.btnApprove);
        btnDeleteCoupon= (Button) findViewById(R.id.btnDeleteCoupon);
        expDate = (TextView) findViewById(R.id.txtExpDate);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
    }
    private void setCouponToViews(ResultSet coupon){
        try {
            txtHeader.setText(coupon.getString(2));
            txtDesc.setText(txtDesc.getText().toString()+coupon.getString(3));
            txtPrice.setText(txtPrice.getText().toString()+coupon.getString(5));
            txtDPrice.setText(txtDPrice.getText().toString()+coupon.getString(6));
            txtBName.setText(txtBName.getText().toString()+coupon.getString(8));
            txtAddress.setText(txtAddress.getText().toString()+coupon.getString(9));
            try {
                expDate.setText(expDate.getText().toString() + coupon.getDate(7));
            }
            catch(Exception e){
                expDate.setText(expDate.getText().toString() + "None");
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Could Not\nLoad Coupon", Toast.LENGTH_LONG);
        }
    }
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnApprove:
                onClickApprove(v);
                break;
            case R.id.btnDeleteCoupon:
                onClickDeleteCoupon(v);
                break;
        }
    }
    public void onClickApprove(View v){
        new AlertDialog.Builder(this)
                .setTitle("Approve Coupon")
                .setMessage("Are you sure you want approve this coupon?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bl.approveCoupon(_couponID);
                        bl.sendApprovedMail(businessName,_couponID,_couponName);
                        approveMsg();
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
    public void approveMsg(){
        Toast.makeText(this, "Coupon was approved", Toast.LENGTH_SHORT).show();
        this.onBackPressed();
    }
    public void onClickDeleteCoupon(View v){
        new AlertDialog.Builder(this)
                .setTitle("Delete Coupon")
                .setMessage("Are you sure you want delete this coupon?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bl.deleteCoupon(_couponID);
                        bl.sendUnApprovedMail(businessName,_couponID,_couponName);
                        deleteMsg();
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
    public void deleteMsg(){
        Toast.makeText(this, "Coupon was deleted", Toast.LENGTH_SHORT).show();
        this.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coupon_approval_page, menu);
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
}