package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientCoupons extends ActionBarActivity {

    private String _userName;
    private String[] _couponsName;
    private String[] _couponsID;
    private String[] _couponsCode;
    private int[] _couponsFulfilled;
    private ListView _listView;
    private ResultSet _allCoupons;
    private IBL bl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_coupons);
        initialize();
    }

    private void initialize() {
        bl = new BL();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _userName = extras.getString("userName");
        }

        _listView = (ListView) findViewById(R.id.listView);
        setListView();
        /* set the fulfilled coupons with a different color */

        couponOnClick();   /*you have to write this line in order for the list view items listener will work*/
    }



    private void couponOnClick(){
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(_couponsFulfilled[position]==1) {
                    Intent intent = new Intent(ClientCoupons.this, RateCouponPage.class);
                    intent.putExtra("userName", _userName);
                    intent.putExtra("couponID", _couponsID[position]);
                    startActivity(intent);
                }

            }
        });
    }


    private void setListView() {
        _allCoupons = bl.getClientCoupons(_userName);
        try
        {
            _allCoupons.last();
            int numOfCoupons;

            if((numOfCoupons=_allCoupons.getRow())>0) {
                String[] toListView = new String[numOfCoupons];
                _couponsName = new String[numOfCoupons];
                _couponsID = new String[numOfCoupons];
                _couponsCode = new String[numOfCoupons];
                _couponsFulfilled = new int[numOfCoupons];
                _allCoupons.beforeFirst();
                int i=0;
                /*set listview items to a string array */
                while (_allCoupons.next()) {
                    _couponsName[i] = _allCoupons.getString(1);
                    _couponsID[i] = _allCoupons.getString(2);
                    _couponsCode[i] = _allCoupons.getString(3);
                    _couponsFulfilled[i] = _allCoupons.getInt(4);
                    if(_couponsFulfilled[i]==0)
                        toListView[i] = "Coupon: " + _couponsName[i] + "\nCode: " + _couponsCode[i];
                    else {
                        toListView[i] = "Coupon: " + _couponsName[i] + "\n************Click To Rate************";
                    }
                    i++;
                }


                /* set the adapter with the string array */
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_item, toListView);

                _listView.setAdapter(adapter);



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }


}
