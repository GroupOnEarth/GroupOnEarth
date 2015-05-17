package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UpdateClientPage extends ActionBarActivity {
    private IBL bl;
    private String userName;
    private EditText userNameTxtField;
    private EditText passwordTxtField;
    private EditText firstNameTxtField;
    private EditText lastNameTxtField;
    private EditText IdTxtField;
    private EditText EmailTxtField;
    private EditText phoneTxtField;
    private EditText genderTxtField;
    private EditText birthdayTxtField;
    private String password="";
    private String firstName="";
    private String lastName="";
    private String phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_client_page);
        bl= new BL();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("userName");
        }
        initialize();
    }
    private void initialize(){
        userNameTxtField = (EditText) findViewById(R.id.fieldUserName);
        userNameTxtField.setEnabled(false);
        passwordTxtField = (EditText) findViewById(R.id.fieldPassword);
        firstNameTxtField = (EditText) findViewById(R.id.fieldFirstName);
        lastNameTxtField = (EditText) findViewById(R.id.fieldLastName);
        IdTxtField = (EditText) findViewById(R.id.fieldID);
        IdTxtField.setEnabled(false);
        EmailTxtField = (EditText) findViewById(R.id.fieldEMail);
        EmailTxtField.setEnabled(false);
        phoneTxtField = (EditText) findViewById(R.id.fieldPhone);
        genderTxtField = (EditText) findViewById(R.id.fieldGender);
        genderTxtField.setEnabled(false);
        birthdayTxtField = (EditText) findViewById(R.id.fieldBirthday);
        birthdayTxtField.setEnabled(false);
        try
        {
            ResultSet userInfo = bl.getUserByUserName(userName);
            userInfo.next();
            userNameTxtField.setText(userInfo.getString(1));
            IdTxtField.setText(userInfo.getString(2));
            passwordTxtField.setText(userInfo.getString(3));
            firstNameTxtField.setText(userInfo.getString(4));
            lastNameTxtField.setText(userInfo.getString(5));
            phoneTxtField.setText(userInfo.getString(6));
            EmailTxtField.setText(userInfo.getString(7));
            ResultSet clientInfo = bl.getClientByUserName(userName);
            clientInfo.next();
            genderTxtField.setText(clientInfo.getString(2));
            birthdayTxtField.setText(clientInfo.getDate(3).toString());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnUpdate:
                onClickUpdate(v);
                break;
        }
    }
    public void onClickUpdate(View v)
    {
        ((TextView) findViewById(R.id.txtFirstName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtLastName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtID)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtEMAIL)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtPhone)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtGender)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtBirthday)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtUserName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtPassword)).setTextColor(Color.BLACK);
        boolean validDetails = true;
        password= String.valueOf((passwordTxtField).getText());
        firstName= String.valueOf((firstNameTxtField).getText());
        lastName= String.valueOf((lastNameTxtField).getText());
        phone= String.valueOf((phoneTxtField).getText());
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
        if (phone.matches("")) {
            ((TextView) findViewById(R.id.txtPhone)).setTextColor(Color.RED);
            validDetails= false;
        }
        if (validDetails)
        {
            bl.updateClientInfo( userName, password, firstName, lastName, phone);
            Toast.makeText(this, userName + " detail was updated!", Toast.LENGTH_LONG).show();
            this.onBackPressed();
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
        getMenuInflater().inflate(R.menu.menu_update_client_page, menu);
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
