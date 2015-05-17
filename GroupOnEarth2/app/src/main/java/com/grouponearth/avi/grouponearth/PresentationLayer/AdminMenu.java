package com.grouponearth.avi.grouponearth.PresentationLayer;
        import android.content.Intent;
        import android.graphics.Color;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
        import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
        import com.grouponearth.avi.grouponearth.R;
public class AdminMenu extends ActionBarActivity {
    private String _userName;
    private TextView header;
    private Button btnLogin;
    private IBL bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);
        header = (TextView)findViewById(R.id.txtHeader);
        bl = new BL();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _userName = extras.getString("userName");
            header.setText("Hello "+_userName);
        }
        if (bl.isUnapprovedExists()) {
            Button btnUAC = (Button) findViewById(R.id.btnWaitingApproval);
            btnUAC.setTextColor(Color.RED);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_menu, menu);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogout:
                onClickLogout();
                Log.d("TKT", "clicked login");
                break;
            case R.id.btnAddNewAdmin:
                onClickAddNewAdmin();
                Log.d("TKT", "clicked btnAddNewAdmin");
                break;
            case R.id.btnAddNewBusiness:
                onClickAddBusiness();
                Log.d("TKT", "clicked btnAddNewAdmin");
                break;
            case R.id.btnWaitingApproval:
                onClickWaitingApproval();
                break;
            case R.id.btnMngUsers:
                onClickMngUsers();
                break;
            case R.id.btnMngBusiness:
                onClickMngBusiness();
                break;
            case R.id.btnManageCoupon:
                onClickMngCoupons();
                break;
        }
    }

    private void onClickMngCoupons() {
        Intent intent = new Intent(this, ManageCouponsGeneric.class);
        intent.putExtra("userType", "Admin");
        startActivity(intent);
    }

    public void onClickLogout()
    {
        this.onBackPressed();
    }
    public void onDestroy()
    {
        super.onDestroy();
    }
    public void onClickAddNewAdmin()
    {
        Intent intent = new Intent(this, AddNewAdminPage.class);
        startActivity(intent);
    }
    public void onClickAddBusiness()
    {
        Intent intent = new Intent(this, AddNewBusinessPage.class);
        startActivity(intent);
    }
    public void onClickWaitingApproval()
    {
        Intent intent = new Intent(this, UnapprovedCouponPage.class);
        startActivity(intent);
    }
    public void onClickMngUsers()
    {
        Intent intent = new Intent(this, UpdateClientListPage.class);
        startActivity(intent);
    }
    public void onClickMngBusiness()
    {
        Toast.makeText(this, "SORRY! Not supported on this version\nPlease submit a formal request", Toast.LENGTH_LONG).show();
    }
    public void onResume(){
        super.onResume();
        Button btnUAC = (Button) findViewById(R.id.btnWaitingApproval);
        if (bl.isUnapprovedExists())
        {
            btnUAC.setTextColor(Color.RED);
        }
        else {
            btnUAC.setTextColor(Color.BLACK);
        }
    }
}