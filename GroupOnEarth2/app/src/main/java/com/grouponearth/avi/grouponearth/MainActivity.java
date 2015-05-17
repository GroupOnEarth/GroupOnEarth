package com.grouponearth.avi.grouponearth;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.DataLayer.DAL;
import com.grouponearth.avi.grouponearth.DataLayer.IDAL;
import com.grouponearth.avi.grouponearth.PresentationLayer.LoginPage;

import java.util.concurrent.CountDownLatch;


public class MainActivity extends ActionBarActivity {


    private TextView adminTxtView;
    private IDAL dal;
    private IBL bl;
    private GifView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //gifView = (GifView) findViewById(R.id.main_gifview);
        setContentView(R.layout.activity_main);
        String stringInfo="";
      /*  stringInfo += "Duration: " + gifView.getMovieDuration() + "\n";
       stringInfo += "W x H: "
                + gifView.getMovieWidth() + " x "
                + gifView.getMovieHeight() + "\n";*/
        //CountDownLatch cd = new CountDownLatch(1);
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
