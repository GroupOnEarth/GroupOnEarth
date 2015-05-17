package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;
import java.sql.Date;
import java.util.Calendar;
public class RegisterPage extends ActionBarActivity {
    private IBL bl;
    private String userName;
    private String firstName;
    private String lastName;
    private String Id;
    private String phone;
    private String Email;
    private String password;
    private String gender;
    private Date birthday;
    Button btnCalendar;
    private int mYear, mMonth, mDay;
    private int chosenYear=0,chosenYearMonth,chosenDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        bl= new BL();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_page, menu);
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
    public void onClickSignUp(View v) {
        ((TextView) findViewById(R.id.txtUserName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtPassword)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtFirstName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtLastName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtID)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtPhone)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtEMAIL)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtGender)).setTextColor(Color.BLACK);
        boolean validDetails = true;
        userName= String.valueOf(((EditText) findViewById(R.id.fieldUserName)).getText());
        password= String.valueOf(((EditText)findViewById(R.id.fieldPassword)).getText());
        firstName= String.valueOf(((EditText)findViewById(R.id.fieldFirstName)).getText());
        lastName= String.valueOf(((EditText) findViewById(R.id.fieldLastName)).getText());
        Id= String.valueOf(((EditText) findViewById(R.id.fieldID)).getText());
        phone= String.valueOf(((EditText) findViewById(R.id.fieldPhone)).getText());
        Email= String.valueOf(((EditText)findViewById(R.id.fieldEMail)).getText());
        birthday= new Date(chosenYear,chosenYearMonth,chosenDay);
        Spinner sGender =  (Spinner)findViewById(R.id.spinnerGender);
        gender= String.valueOf(sGender.getSelectedItem());
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
        if (chosenYear == 0) {
            ((TextView) findViewById(R.id.txtBirthday)).setTextColor(Color.RED);
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
                    bl.addSystemUser( userName, Id, password, firstName, lastName, phone, Email, gender, birthday);
                    Toast.makeText(this, userName +" was added!", Toast.LENGTH_LONG).show();
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
    public void onClickSelectDate(View v) {
        btnCalendar = (Button) findViewById(R.id.buttonSelectDate);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
            {
                btnCalendar.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                chosenYear= year-1900;
                chosenYearMonth= monthOfYear + 1;
                chosenDay=dayOfMonth;
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }
}