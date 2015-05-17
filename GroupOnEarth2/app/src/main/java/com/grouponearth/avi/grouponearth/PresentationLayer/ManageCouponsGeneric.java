package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageCouponsGeneric extends ActionBarActivity {

    private ResultSet _allCoupons;
    private String _businessName;
    private String _userName;
    private String _userType;
    private String[] _couponsName;
    private String[] _couponsID;
    private IBL bl;
    private ListView _listCoupons;
    private boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_coupons_generic);
        initialize();
    }

    private void initialize() {
        _userName = "c";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _userType = extras.getString("userType");
            if(_userType.equals("BusinessManager")) {
                _businessName = extras.getString("businessName");
                isAdmin = false;
            }
            else
                isAdmin = true;
        }
        bl = new BL();
        _listCoupons = (ListView) findViewById(R.id.manage_listView);

        setListView();
        couponOnClick();
    }

    private void setListView() {
        if(isAdmin){
            _allCoupons = bl.getAllCoupons();
        }
        else{
            _allCoupons = bl.getBusinessCoupons(_businessName);
        }
        try
        {
            _allCoupons.last();
            int numOfCoupons;
            if((numOfCoupons=_allCoupons.getRow())>0) {
                _couponsName = new String[numOfCoupons];
                _couponsID = new String[numOfCoupons];
                _allCoupons.beforeFirst();
                int i=0;
                while (_allCoupons.next()) {
                    _couponsName[i] = _allCoupons.getString(2);
                    _couponsID[i] = _allCoupons.getString(1);
                    i++;
                }
            }
            else{
                _couponsName = new String[0];
                _couponsID = new String[0];
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_item, _couponsName);

            _listCoupons.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void couponOnClick(){
        _listCoupons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isAdmin){
                    Intent intent = new Intent(ManageCouponsGeneric.this, UpdateCouponPage.class);

                    intent.putExtra("userName", _userName);
                    intent.putExtra("couponID", _couponsID[position]);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(ManageCouponsGeneric.this, BusinessManageCoupon.class);
                    intent.putExtra("couponID", _couponsID[position]);
                    startActivity(intent);
                }

            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }



}
