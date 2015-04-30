package com.grouponearth.avi.grouponearth;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.DataLayer.DAL;
import com.grouponearth.avi.grouponearth.DataLayer.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Currency;
import java.util.Date;

import javax.xml.transform.Result;


public class MainActivity extends ActionBarActivity {


    Button btnLogin;
    Button btnSign;
    private TextView adminTxtView;
    private DAL dal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dal = new DAL();


       /* Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);*/

    }

    public void onClick(View v){
        Button btn = (Button)v;
        Intent intent = new Intent(this, LoginPage.class);
        this.startActivity(intent);
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
