package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
public class UnapprovedCouponPage extends ActionBarActivity {
    private IBL bl;
    private ResultSet _unapprovedCoupons;
    private ListView _listCoupons;
    private String[] _couponsName;
    private String[] _couponsID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unapproved_coupon_page);
        bl = new BL();
        initialize();
    }
    private void initialize() {
        _listCoupons = (ListView) findViewById(R.id.listCoupons);
        _unapprovedCoupons = bl.getUnapprovedCoupons();
        setListView();
        couponOnClick();
    }
    private void setListView() {
        try
        {
            _unapprovedCoupons.last();
            int numOfCoupons;
            if((numOfCoupons=_unapprovedCoupons.getRow())>0) {
                _couponsName = new String[numOfCoupons];
                _couponsID = new String[numOfCoupons];
                _unapprovedCoupons.beforeFirst();
                int i=0;
                while (_unapprovedCoupons.next()) {
                    _couponsName[i] = _unapprovedCoupons.getString(2);
                    _couponsID[i] = _unapprovedCoupons.getString(1);
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
                Intent intent = new Intent(UnapprovedCouponPage.this, CouponApprovalPage.class);
                intent.putExtra("couponID", _couponsID[position]);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_unapproved_coupon_page, menu);
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
    public void onResume(){
        super.onResume();
        initialize();
    }
}