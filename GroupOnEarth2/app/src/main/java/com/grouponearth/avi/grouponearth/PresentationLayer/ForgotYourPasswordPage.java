package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.util.Arrays;
import java.util.List;

public class ForgotYourPasswordPage extends ActionBarActivity {

    private IBL bl;
    private EditText inputMail;
    private String mailAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_your_password_page);
        bl = new BL();
    }


    public void sendButtonClicked(View v) {
        inputMail = (EditText)findViewById(R.id.inputMail);
        mailAddress= inputMail.getText().toString();
        if ( bl.isMailExists(mailAddress))
        {

            String fromEmail = "GroupOnEarth@gmail.com";
            String fromPassword = "grouponearth1";
            String toEmails = mailAddress;
            List<String> toEmailList = Arrays.asList(toEmails.split("\\s*,\\s*"));
            String emailSubject = "Password Remainder";
            String emailBody = "HELLO";
            //new SendMailTask(this).execute(fromEmail,fromPassword, toEmailList, emailSubject, emailBody);




            Toast.makeText(this, "Mail was sent", Toast.LENGTH_LONG).show();
        }
        else
        {
            SpannableString msg = new SpannableString("User Doesn't Exist");
            msg.setSpan(new ForegroundColorSpan(Color.RED), 0, 18, 0);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
        this.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot_your_password_page, menu);
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

    public void onDestroy(){
        this.onBackPressed();
    }
}
