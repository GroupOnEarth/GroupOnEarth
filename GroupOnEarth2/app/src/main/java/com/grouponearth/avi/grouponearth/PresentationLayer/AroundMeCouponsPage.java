package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AroundMeCouponsPage extends ActionBarActivity implements LocationListener {
    private IBL bl;
    private String userName;
    private double longitude;
    private double latitude;
    private Button btnSearch;
    private EditText editDistance;
    private ResultSet _aroundMeCoupons;
    private ListView _listCoupons;
    private String[] _couponsName;
    private String[] _couponsID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_me_coupons_page);
        initialize();
    }
    private void initialize() {
        bl = new BL();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("userName");
            longitude= extras.getDouble("longitude");
            latitude= extras.getDouble("latitude");
        }
        _listCoupons = (ListView) findViewById(R.id.listCoupons);
        _aroundMeCoupons = bl.getCouponsByDistance(longitude, latitude, 5);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        editDistance = (EditText) findViewById(R.id.editDistance);
        setListView();
        couponOnClick();
    }
    private void setListView() {
        try
        {
            _aroundMeCoupons.last();
            int numOfCoupons;
            if((numOfCoupons=_aroundMeCoupons.getRow())>0) {
                _couponsName = new String[numOfCoupons];
                _couponsID = new String[numOfCoupons];
                _aroundMeCoupons.beforeFirst();
                int i=0;
                while (_aroundMeCoupons.next()) {
                    _couponsName[i] = _aroundMeCoupons.getString(2);
                    _couponsID[i] = _aroundMeCoupons.getString(1);
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
                Intent intent = new Intent(AroundMeCouponsPage.this, CouponPage.class);
                intent.putExtra("userName", userName);
                intent.putExtra("couponID", _couponsID[position]);
                startActivity(intent);
            }
        });
    }
    public void onClick(View v)
    {
        switch (v.getId()){
            case(R.id.btnSearch):
                onClickSearch();
                break;
        }
    }
    private void onClickSearch()
    {
        String distanceString = editDistance.getText().toString();
        int distance= Integer.parseInt(distanceString);
        _aroundMeCoupons = bl.getCouponsByDistance(longitude, latitude, distance);
        setListView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_around_me_coupons_page, menu);
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
    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
}