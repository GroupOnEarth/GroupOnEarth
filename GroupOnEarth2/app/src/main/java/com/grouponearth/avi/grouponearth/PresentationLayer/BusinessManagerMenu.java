package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.grouponearth.avi.grouponearth.R;

import org.w3c.dom.Text;

public class BusinessManagerMenu extends ActionBarActivity {

    private TextView txtHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_manager_menu);
        txtHeader = (TextView)findViewById(R.id.txtHeader);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_business_manager_menu, menu);
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
                Log.d("TKT", "clicked logout");
                break;

        }

    }

    public void onClickLogout(){
        this.onBackPressed();
    }

    public String fitText(String s, TextView v){
        while(s.contains("\n")){
            s = deleteEnter(s);
            v.setTextSize(v.getTextSize()-1);
            v.setText(s);
        }
        return s;

    }

    public String deleteEnter(String s){
        if(s.contains("\n")){
            int index = s.indexOf('\n');
            s = s.substring(0,index) + " " + s.substring(index+1);
        }
        return s;
    }


}