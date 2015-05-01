package com.grouponearth.avi.grouponearth;

import android.content.Intent;
<<<<<<< HEAD
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
=======
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.DataLayer.DAL;
import com.grouponearth.avi.grouponearth.DataLayer.IDAL;
import com.grouponearth.avi.grouponearth.PresentationLayer.LoginPage;


public class MainActivity extends ActionBarActivity {


    Button btnLogin;
    Button btnSign;
    private TextView adminTxtView;
    private IDAL dal;
    private IBL bl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
<<<<<<< HEAD
        setContentView(R.layout.activity_main);
=======
        /*dal = new DAL();*/
        setContentView(R.layout.activity_main);

>>>>>>> dde6ebd64ee5f46ce85188de2b45d0d25998cb8d

        dal = new DAL();
        bl= new BL(dal);

        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
