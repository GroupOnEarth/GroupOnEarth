package com.grouponearth.avi.grouponearth.PresentationLayer;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grouponearth.avi.grouponearth.BusinessLayer.BL;
import com.grouponearth.avi.grouponearth.BusinessLayer.IBL;
import com.grouponearth.avi.grouponearth.R;

import java.sql.Date;
import java.util.Calendar;

import static com.grouponearth.avi.grouponearth.R.id.addCoupon_ID;
import static com.grouponearth.avi.grouponearth.R.id.txtDPrice;
import static com.grouponearth.avi.grouponearth.R.id.txtPrice;


public class BusinessAddNewCoupon extends ActionBarActivity {

    IBL bl;

    private TextView viewTxtId;
    private TextView viewTxtName;
    private TextView viewTxtDesc;
    private TextView viewTxtPrice;
    private TextView viewTxtDPrice;


    private TextView txtID;
    private TextView txtName;
    private TextView txtDesc;
    private TextView txtPrice;
    private TextView txtDiscount;
    private Button btnCalendar;
    private Spinner spinnerCategory;
    private Button btnAddNew;
    private int mYear, mMonth, mDay;
    private String _businessName;
    private int chosenYear=0,chosenYearMonth,chosenDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_add_new_coupon);
        initialize();
    }

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _businessName = extras.getString("businessName");
        }
        viewTxtId = (TextView) findViewById(R.id.addCoupon_txtID);
        viewTxtName = (TextView) findViewById(R.id.addCoupon_txtname);
        viewTxtDesc = (TextView) findViewById(R.id.addCoupon_txtDesc);
        viewTxtPrice = (TextView) findViewById(R.id.addCoupon_txtPrice);
        viewTxtDPrice = (TextView) findViewById(R.id.addCoupon_txtDPrice);


        txtID = (TextView) findViewById(R.id.addCoupon_ID);
        txtName = (TextView) findViewById(R.id.addCoupon_name);
        txtDesc = (TextView) findViewById(R.id.addCoupon_description);
        txtPrice = (TextView) findViewById(R.id.addCoupon_price);
        txtDiscount = (TextView) findViewById(R.id.addCoupon_DPrice);
        btnCalendar = (Button) findViewById(R.id.addCoupon_caleneder);
        spinnerCategory = (Spinner) findViewById(R.id.newCoupon_spinnerCategory);
        btnAddNew = (Button) findViewById(R.id.addCoupon_btnAdd);
        bl = new BL();
    }

    public void onClick(View v){
        switch (v.getId()){
            case (R.id.addCoupon_btnAdd):
                onClickAddCoupon();
                break;
        }
    }

    public void onClickAddCoupon(){
        viewTxtDPrice.setTextColor(Color.BLACK);
        viewTxtPrice.setTextColor(Color.BLACK);
        viewTxtName.setTextColor(Color.BLACK);
        viewTxtId.setTextColor(Color.BLACK);
        viewTxtDesc.setTextColor(Color.BLACK);



        boolean isValidDetails = true;
        String ID = txtID.getText().toString();
        String name = txtName.getText().toString();
        String desc = txtDesc.getText().toString();
        int dPrice = 0;
        int price = 0;
        try {
            price = Integer.parseInt(txtPrice.getText().toString());
            dPrice = Integer.parseInt(txtDiscount.getText().toString());
        }
        catch (NumberFormatException e){
            isValidDetails = false;
            viewTxtPrice.setTextColor(Color.RED);
            viewTxtDPrice.setTextColor(Color.RED);

        }
        Date experationDate = new Date(chosenYear,chosenYearMonth,chosenDay);
        String category = spinnerCategory.getSelectedItem().toString();
        if (isValidDetails && ID.matches("")) {
            viewTxtId.setTextColor(Color.RED);
            isValidDetails= false;
        }
        if (isValidDetails && name.matches("")) {
            viewTxtName.setTextColor(Color.RED);
            isValidDetails= false;
        }
        if (isValidDetails && desc.matches("")) {
           viewTxtDesc.setTextColor(Color.RED);
            isValidDetails= false;
        }
        if (isValidDetails && chosenYear == 0) {
            btnCalendar.setTextColor(Color.RED);
            isValidDetails= false;
        }
        if(isValidDetails && spinnerCategory.getSelectedItem().toString().equals("All Categories")){
            spinnerCategory.setBackgroundColor(Color.RED);
            isValidDetails = false;
        }

        if(!isValidDetails){
            Toast toast = Toast.makeText(this, "Invalid Details", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            if(bl.isCouponExists(ID)){
                Toast toast = Toast.makeText(this, "Coupon ID\nAlready Exists", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                bl.addCoupon(ID, name, desc, category, price, dPrice, experationDate, _businessName);
                Toast toast = Toast.makeText(this, "Coupon Added\nSuccessfully!", Toast.LENGTH_SHORT);
                toast.show();
                this.onBackPressed();
            }
        }
    }

    public void onClickSelectDate(View v) {
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
