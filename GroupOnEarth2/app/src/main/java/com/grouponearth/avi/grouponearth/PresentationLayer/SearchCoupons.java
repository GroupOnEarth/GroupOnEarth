package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class SearchCoupons extends ActionBarActivity {

    private Spinner spinnterCat;
    private Spinner spinnerBusiness;
    private Button btnSearch;
    private EditText editSearch;
    private ResultSet _allCoupons;
    private ListView _listCoupons;
    private RadioButton _radioCat;
    private RadioButton _radioPre;
    private RadioButton _radioBusiness;
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


    public void initialilze() {
        bl = new BL();
        _listCoupons = (ListView) findViewById(R.id.listCoupons);
        spinnterCat = (Spinner) findViewById(R.id.spinnerCategory);
        _allCoupons = bl.getCouponsByName("");
        btnSearch = (Button) findViewById(R.id.btnSearch);
        _radioPre = (RadioButton) findViewById(R.id.radioPre);
        _radioCat = (RadioButton) findViewById(R.id.radioCat);
        _radioBusiness = (RadioButton) findViewById(R.id.radioBusiness);
        editSearch = (EditText) findViewById(R.id.editSearch);
        spinnerBusiness = (Spinner) findViewById(R.id.spinnerBusinesses);
        spinnerBusiness.setVisibility(View.GONE);
        ArrayList<String> businessesNames = getBusinessesList();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.list_view_item, businessesNames);
        spinnerAdapter.setDropDownViewResource(R.layout.list_view_item);
        spinnerBusiness.setAdapter(spinnerAdapter);
        setListView();
        couponOnClick();

    }

    private ArrayList<String> getBusinessesList() {
        ArrayList<String> businessArray = new ArrayList<String>();
        ResultSet rs = bl.getAllBusinesses();
        try {
            while (rs.next()) {
                businessArray.add(rs.getString(1));
            }
        } catch (SQLException e) {

        }
        return businessArray;
    }

    private void setListView() {
        try {
            _allCoupons.last();
            int numOfCoupons;
            if ((numOfCoupons = _allCoupons.getRow()) > 0) {
                _couponsName = new String[numOfCoupons];
                _couponsID = new String[numOfCoupons];
                _allCoupons.beforeFirst();
                int i = 0;
                while (_allCoupons.next()) {
                    _couponsName[i] = _allCoupons.getString(2);
                    _couponsID[i] = _allCoupons.getString(1);
                    i++;
                }
            } else {
                _couponsName = new String[0];
                _couponsID = new String[0];
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_item, _couponsName);

            _listCoupons.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void couponOnClick() {
        _listCoupons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchCoupons.this, CouponPage.class);
                intent.putExtra("userName", _userName);
                intent.putExtra("couponID", _couponsID[position]);
                startActivity(intent);
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnSearch):
                onClickSearch();
                break;
            case (R.id.editSearch):
                onClickSearchBox(v);
                break;
            case (R.id.radioCat):
                onClickRadioCat(v);
                break;
            case (R.id.radioBusiness):
                onClickRadioBusiness(v);
                break;
            case (R.id.radioPre):
                onClickRadioPre(v);
                break;
        }
    }

    private void onClickRadioBusiness(View v) {
        spinnerBusiness.setVisibility(View.VISIBLE);
        spinnterCat.setVisibility(View.GONE);
        _radioPre.setChecked(false);
        _radioCat.setChecked(false);

    }

    private void onClickRadioCat(View v) {
        spinnterCat.setVisibility(View.VISIBLE);
        spinnerBusiness.setVisibility(View.GONE);
        _radioPre.setChecked(false);
        _radioBusiness.setChecked(false);

    }

    private void onClickRadioPre(View v) {
        spinnterCat.setVisibility(View.GONE);
        spinnerBusiness.setVisibility(View.GONE);
        _radioCat.setChecked(false);
        _radioBusiness.setChecked(false);

    }

    private void onClickSearchBox(View v) {
        editSearch.setText("");
    }

    private void onClickSearch() {
        String query = editSearch.getText().toString();
        String cat = spinnterCat.getSelectedItem().toString();
        String businessName = spinnerBusiness.getSelectedItem().toString();
        if (_radioCat.isChecked()) {
            if (cat.equals("All Categories")) {
                _allCoupons = bl.getCouponsByName(query);
            } else {
                _allCoupons = bl.getCouponsByNameAndCategory(query, cat);
            }
        } else {
            if (_radioPre.isChecked()) {
                _allCoupons = bl.getCouponsByPreferences(_userName, query);
            } else {
                _allCoupons = bl.getCouponsByBusiness(businessName, query);
            }

            setListView();
        }


    }
}

