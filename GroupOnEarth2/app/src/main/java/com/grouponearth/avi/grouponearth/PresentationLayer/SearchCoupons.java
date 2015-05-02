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
import android.widget.SearchView;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

public class SearchCoupons extends ActionBarActivity {


        private SearchView _searchView;
        private ResultSet _allCoupons;
        private ListView _listCoupons;
        private String _userName;
        private String[] _couponsName;
        private String[] _couponsID;
        private IBL bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_coupons);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _userName = extras.getString("userName");
        }
        initialilze();
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

    public void initialilze(){
        bl = new BL();

        _searchView = (SearchView) findViewById(R.id.searchView);
        _searchView.setQueryHint("Search Coupons");
        _listCoupons = (ListView) findViewById(R.id.listCoupons);
        _allCoupons = bl.getAllCoupons();
        setListView();
        couponOnClick();
    }


    private void setListView() {
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

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_item, _couponsName);

                _listCoupons.setAdapter(adapter);
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    private void couponOnClick(){
        _listCoupons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView couponClicked = (TextView)view;
                Intent intent = new Intent(SearchCoupons.this, CouponPage.class);
                intent.putExtra("userName", _userName);
                intent.putExtra("couponID", _couponsID[position]);
                startActivity(intent);
            }
        });
    }

    }

