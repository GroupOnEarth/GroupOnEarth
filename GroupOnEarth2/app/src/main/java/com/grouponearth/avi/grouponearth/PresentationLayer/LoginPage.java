package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Avi on 29/04/2015.
 */
public class LoginPage extends Activity implements View.OnClickListener {

    private IBL bl;
    private EditText inputUserName;
    private EditText inputPassword;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnForgotPass;




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Log.d("CREATE", "login on create");
        inputUserName   = (EditText)findViewById(R.id.inputUserName);
        inputPassword   = (EditText)findViewById(R.id.inputPass);


    }

    protected void onClickLogin(View v){
        btnLogin = (Button)v;

        bl = new BL();
        String uName = inputUserName.getText().toString();
        String pass = inputPassword.getText().toString();
        String userType = "";
        if(!("".equals(uName)) & !("".equals(pass))){
            Log.d("TKT", "inserted non empty user name and pass");
            if(!(userType = bl.confirmLogin(uName, pass)).equals(""))
            {
                inputUserName.setText("");
                inputPassword.setText("");
                if(userType.equals("Admin"))
                {
                    Log.d("hellooooo","helllo");
                    Intent intent = new Intent(this, AdminMenu.class);
                    intent.putExtra("userName", uName);
                    startActivity(intent);
                }
                else if(userType.equals("BusinessManager"))
                {
                    Intent intent = new Intent(this, BusinessManagerMenu.class);
                    intent.putExtra("userName", uName);
                    startActivity(intent);
                }
                else if(userType.equals("Client"))
                {
                    Intent intent = new Intent(this, ClientMenu.class);
                    intent.putExtra("userName", uName);
                    startActivity(intent);
                }
                else
                {
                    Log.e("ERROR", "No such user type");
                }
            }
            else
            {
                Toast.makeText(this, "Invalid User Name\nOr Password", Toast.LENGTH_LONG).show();
            }
        }

    }

    protected void onClickRegister(View v){
        btnRegister = (Button)v;
        btnRegister.setText("clicked");
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);

    }

    protected void onClickForgotPass(View v){
        btnForgotPass = (Button)v;
        Intent intent = new Intent(this, ForgotYourPasswordPage.class);
        // intent.putExtra("IBL", (java.io.Serializable) bl);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogin:
                onClickLogin(v);
                Log.d("TKT", "clicked login");
                break;
            case R.id.btnRegister:
                onClickRegister(v);
                Log.d("TKT", "clicked register");
                break;
            case R.id.btnForgotPass:
                onClickForgotPass(v);
                Log.d("TKT", "clicked pass");
                break;
        }

    }
}
