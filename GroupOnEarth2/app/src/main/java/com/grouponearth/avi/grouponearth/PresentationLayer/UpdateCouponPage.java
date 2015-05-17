package com.grouponearth.avi.grouponearth.PresentationLayer;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
public class UpdateCouponPage extends ActionBarActivity {
    private IBL bl;
    private String couponID;
    private EditText fieldCouponID;
    private EditText fieldCouponName;
    private EditText fieldCouponDescription;
    private Spinner spinnerCategory;
    private EditText fieldCouponPrice;
    private EditText fieldDiscountPrice;
    private Button btnCalendar;
    private Button btnUpdate;
    private int mYear, mMonth, mDay;
    private int chosenYear=0,chosenMonth,chosenDay;
    private EditText fieldBusinessName;
    private String name="";
    private String desc="";
    private String category="";
    private int price;
    private int disPrice;
    private Date expDate=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_coupon_page);
        bl= new BL();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            couponID = extras.getString("couponID");
        }
        initialize();
    }
    private void initialize(){
        fieldCouponID = (EditText) findViewById(R.id.fieldCouponID);
        fieldCouponID.setEnabled(false);
        fieldCouponName = (EditText) findViewById(R.id.fieldCouponName);
        fieldCouponDescription = (EditText) findViewById(R.id.fieldCouponDescription);
        spinnerCategory= (Spinner) findViewById(R.id.spinnerCategory);
        fieldCouponPrice = (EditText) findViewById(R.id.fieldCouponPrice);
        fieldDiscountPrice= (EditText) findViewById(R.id.fieldDiscountPrice);
        btnCalendar= (Button) findViewById(R.id.updateCouponCalendar);
        btnUpdate= (Button) findViewById(R.id.btnUpdate);
        fieldBusinessName= (EditText) findViewById(R.id.fieldBusinessName);
        fieldBusinessName.setEnabled(false);
        try
        {
            ResultSet couponInfo = bl.getCouponByID(couponID);
            couponInfo.next();
            fieldCouponID.setText(couponInfo.getString(1));
            fieldCouponName.setText(couponInfo.getString(2));
            fieldCouponDescription.setText(couponInfo.getString(3));
            String cat= couponInfo.getString(4);
            spinnerCategory.setSelection(getIndex(spinnerCategory, cat));
            fieldCouponPrice.setText("" + couponInfo.getInt(5));
            fieldDiscountPrice.setText("" + couponInfo.getInt(6));
            expDate=  couponInfo.getDate(7);
            btnCalendar.setText(expDate.toString());
            fieldBusinessName.setText(couponInfo.getString(8));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    public void onClick(View v){
        switch (v.getId()){
            case (R.id.btnUpdate):
                onClickUpdateCoupon();
                break;
            case (R.id.btnDelete):
                onClickDeleteCoupon();
                break;
        }
    }
    public void onClickUpdateCoupon(){
        ((TextView) findViewById(R.id.txtCouponID)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtCouponName)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtCouponDescription)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtCouponPrice)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtDiscountPrice)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtExpDate)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.txtBusinessName)).setTextColor(Color.BLACK);
        boolean isValidDetails = true;
        name= String.valueOf((fieldCouponName).getText());
        desc= String.valueOf((fieldCouponDescription).getText());
        category= spinnerCategory.getSelectedItem().toString();
        try
        {
            price = Integer.parseInt(fieldCouponPrice.getText().toString());
        }
        catch (NumberFormatException e){
            isValidDetails = false;
            ((TextView) findViewById(R.id.txtCouponPrice)).setTextColor(Color.RED);
        }
        try
        {
            disPrice = Integer.parseInt(fieldDiscountPrice.getText().toString());
        }
        catch (NumberFormatException e){
            isValidDetails = false;
            ((TextView) findViewById(R.id.txtDiscountPrice)).setTextColor(Color.RED);
        }
        if (chosenYear != 0)
        {
            expDate = new Date(chosenYear, chosenMonth, chosenDay);
        }
        if (isValidDetails && name.matches("")) {
            ((TextView) findViewById(R.id.txtCouponName)).setTextColor(Color.RED);
            isValidDetails= false;
        }
        if (isValidDetails && desc.matches("")) {
            ((TextView) findViewById(R.id.txtCouponDescription)).setTextColor(Color.RED);
            isValidDetails= false;
        }
        if (isValidDetails && category.matches("All Categories")) {
            spinnerCategory.setBackgroundColor(Color.RED);
            isValidDetails= false;
        }
        if(!isValidDetails)
        {
            Toast toast = Toast.makeText(this, "Invalid Details", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            bl.updateCouponInfo(couponID, name, desc, category, price, disPrice, expDate);
            Toast toast = Toast.makeText(this, "Coupon was updated Successfully!", Toast.LENGTH_SHORT);
            toast.show();
            this.onBackPressed();
        }
    }
    private void onClickDeleteCoupon() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Coupon")
                .setMessage("Are you sure you want Delete this coupon?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bl.deleteCoupon(couponID);
                        deletedMsg();
                        onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void deletedMsg()
    {
        Toast.makeText(this, "Coupon Deleted", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_coupon_page, menu);
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
                chosenMonth= monthOfYear;
                chosenDay=dayOfMonth;
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }
}