package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private EditText inputUserName;
    private EditText inputPassword;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnForgotPass;




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


    }

    protected void onClickLogin(View v){
        btnLogin = (Button)v;
        btnLogin.setText("clicked");

    }

    protected void onClickRegister(View v){
        btnRegister = (Button)v;
        btnRegister.setText("clicked");
    }

    protected void onClickForgotPass(View v){
        btnForgotPass = (Button)v;
        btnForgotPass.setText("clicked");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogin:
                onClickLogin(v);
                break;
            case R.id.btnRegister:
                onClickRegister(v);
                break;
            case R.id.btnForgotPass:
                onClickForgotPass(v);
                break;
        }

    }
}
