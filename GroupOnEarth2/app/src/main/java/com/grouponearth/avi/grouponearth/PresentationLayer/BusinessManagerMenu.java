package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

public class BusinessManagerMenu extends ActionBarActivity {

    private TextView txtHeader;
    private String _businessName;
    private String _userName;
    private IBL bl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_manager_menu);
        initialize();

    }

    private void initialize() {
        bl = new BL();
        txtHeader = (TextView)findViewById(R.id.txtHeader);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _userName = extras.getString("userName");
        }
        _businessName = bl.getBusinessName(_userName);
    }


    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogout:
                onClickLogout();
                Log.d("TKT", "clicked logout");
                break;
            case R.id.btnAddCoupon:
                onClickAddCoupon(v);
                break;
            case R.id.btnOurCoupons:
                onClickOurCoupons(v);
                break;
            case R.id.business_btnEdit:
                onClickEditProfile(v);
                break;

        }

    }

    private void onClickEditProfile(View v) {
        Intent intent = new Intent(this, BusinessEditProfile.class);
        intent.putExtra("businessName", _businessName);
        startActivity(intent);
    }


    public void onClickOurCoupons(View v){
        Intent intent = new Intent(this, ManageCouponsGeneric.class);
        intent.putExtra("businessName", _businessName);
        intent.putExtra("userType", "BusinessManager");
        startActivity(intent);
    }

    public void onClickAddCoupon(View v){
        Intent intent = new Intent(this, BusinessAddNewCoupon.class);
        intent.putExtra("businessName", _businessName);
        startActivity(intent);
    }

    public void onClickLogout(){
        this.onBackPressed();
    }

    public String fitText(String s, TextView v){
        while(s.contains("\n")){
            s = deleteEnter(s);
            v.setTextSize(v.getTextSize()-1);
            v.setText(s);
        }
        return s;

    }

    public String deleteEnter(String s){
        if(s.contains("\n")){
            int index = s.indexOf('\n');
            s = s.substring(0,index) + " " + s.substring(index+1);
        }
        return s;
    }


}