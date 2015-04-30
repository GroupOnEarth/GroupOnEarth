package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.grouponearth.avi.grouponearth.R;

/**
 * Created by Avi on 29/04/2015.
 */
public class LoginPage extends Activity {

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

    }

    protected void onClickRegister(View v){

    }

    protected void onClickForgotPass(View v){

    }

}
