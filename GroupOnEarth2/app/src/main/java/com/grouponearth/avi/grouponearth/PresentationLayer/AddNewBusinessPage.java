package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;
import java.io.IOException;
import java.util.List;
public class AddNewBusinessPage extends ActionBarActivity {
    private IBL bl;
    private String userName= "";
    private String firstName= "";
    private String lastName= "";
    private String Id= "";
    private String phone= "";
    private String Email= "";
    private String password= "";
    private String businessName= "";
    private String address= "";
    private String desc= "";
    private double latitude;
    private double longitude;
    private List<Address> geocodeMatches = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_business_page);
        bl= new BL();
    }

    public void onClickAddBusiness(View v) {
        ((TextView) findViewById(R.id.txtUserName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtPassword)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtFirstName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtLastName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtID)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtPhone)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtEMAIL)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtBusinessName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtAddress)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtDescription)).setTextColor(Color.BLACK);
        boolean validDetails = true;
        userName= String.valueOf(((EditText) findViewById(R.id.fieldUserName)).getText());
        password= String.valueOf(((EditText)findViewById(R.id.fieldPassword)).getText());
        firstName= String.valueOf(((EditText)findViewById(R.id.fieldFirstName)).getText());
        lastName= String.valueOf(((EditText) findViewById(R.id.fieldLastName)).getText());
        Id= String.valueOf(((EditText) findViewById(R.id.fieldID)).getText());
        phone= String.valueOf(((EditText) findViewById(R.id.fieldPhone)).getText());
        Email= String.valueOf(((EditText)findViewById(R.id.fieldEMail)).getText());
        businessName= String.valueOf(((EditText)findViewById(R.id.fieldBusinessName)).getText());
        address= String.valueOf(((EditText)findViewById(R.id.fieldAddress)).getText());
        desc= String.valueOf(((EditText)findViewById(R.id.fieldDescription)).getText());

        if (userName.matches("")) {
            ((TextView) findViewById(R.id.txtUserName)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (password.matches("")) {
            ((TextView) findViewById(R.id.txtPassword)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (firstName.matches("")) {
            ((TextView) findViewById(R.id.txtFirstName)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (lastName.matches("")) {
            ((TextView) findViewById(R.id.txtLastName)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (Id.matches("")) {
            ((TextView) findViewById(R.id.txtID)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (phone.matches("")) {
            ((TextView) findViewById(R.id.txtPhone)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (Email.matches("")) {
            ((TextView) findViewById(R.id.txtEMAIL)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (businessName.matches("")) {
            ((TextView) findViewById(R.id.txtBusinessName)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (address.matches("")) {
            ((TextView) findViewById(R.id.txtAddress)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (validDetails)
        {
            if (bl.isUserExists(userName))
            {
                ((TextView) findViewById(R.id.txtUserName)).setTextColor(Color.RED);
                Toast toast = Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT);
                TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
                tv.setTextColor(Color.RED);
                toast.show();
            }
            else
            {
                if (bl.isMailExists(Email))
                {
                    ((TextView) findViewById(R.id.txtEMAIL)).setTextColor(Color.RED);
                    Toast toast = Toast.makeText(this, "E-mail already exists", Toast.LENGTH_SHORT);
                    TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
                    tv.setTextColor(Color.RED);
                    toast.show();
                }
                else
                {
                    try
                    {
                        geocodeMatches =
                                new Geocoder(this).getFromLocationName(address, 1);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    if (!geocodeMatches.isEmpty())
                    {
                        longitude = geocodeMatches.get(0).getLongitude();
                        latitude = geocodeMatches.get(0).getLatitude();
                    }
                    bl.addSystemUser( userName, Id, password, firstName, lastName, phone, Email, "BusinessManager");
                    bl.addBusiness(userName,businessName,address,desc,longitude, latitude);
                    Toast.makeText(this,"Business was added!", Toast.LENGTH_LONG).show();
                    this.onBackPressed();
                }
            }
        }
        else
        {
            Toast toast = Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT);
            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            tv.setTextColor(Color.RED);
            toast.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_business_page, menu);
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