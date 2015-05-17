package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.graphics.Color;
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
public class ForgotYourPasswordPage extends ActionBarActivity {
    private IBL bl;
    private String mail="";
    private String username="";
    private String password= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_your_password_page);
        bl= new BL();
    }
    public void onClickSend(View v) {
        mail= String.valueOf(((EditText)findViewById(R.id.fieldEmail)).getText());
        if (bl.isMailExists(mail))
        {
            username= bl.getUserByMail(mail);
            password= bl.getPasswordByMail(mail);
            bl.sendMail(username,password,mail);
            Toast.makeText(this,"Mail was Sent", Toast.LENGTH_LONG).show();
            this.onBackPressed();
        }
        else
        {
            Toast toast = Toast.makeText(this, "Couldn't find user", Toast.LENGTH_SHORT);
            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            tv.setTextColor(Color.RED);
            toast.show();
        }
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
}