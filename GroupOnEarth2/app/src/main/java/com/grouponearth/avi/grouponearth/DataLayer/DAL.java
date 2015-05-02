package com.grouponearth.avi.grouponearth.DataLayer;

/**
 * Created by Avi on 01/05/2015.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class DAL implements IDAL{
    private DBConnect dbConnect;
    private static Connection con;


    public DAL() {
        AsyncTask at = new DBConnect();
        at.execute();
        try {
            con = (Connection) at.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }




    @Override
    public ResultSet getSystemUsers() {
        return null;
    }
    /* The function getClient retrieves information about a given Client.
    *
            * @param _UserName
    *
            * @pre: @param _UserName exists in database.
            * @post: none.
    */
    public ResultSet getClient(String _UserName)
    {
        CallableStatement getC;
        try
        {
            getC = (CallableStatement) con.prepareCall("call get_client (?)");
            getC.setString(1, _UserName);
            return getC.executeQuery();

        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public ResultSet getUser(String _UserName)
    {
        CallableStatement getC;
        try
        {
            getC = (CallableStatement) con.prepareCall("call get_user (?)");
            getC.setString(1, _UserName);
            return getC.executeQuery();

        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public ResultSet getCouponByID(String couponID){
        CallableStatement getCoupon;
        try{
            getCoupon = (CallableStatement) con.prepareCall("call getCouponByID (?)");
            getCoupon.setString(1, couponID);
            return getCoupon.executeQuery();
        }
        catch (SQLException e){

        }
        return null;
    }

    @Override
    public boolean addPurchase(String _userName, String _couponID, String _serial) {
        CallableStatement addPurchase;
        try{
            addPurchase = (CallableStatement) con.prepareCall("call add_coupon_purchase(?,?,?)");
            addPurchase.setString(1, _userName);
            addPurchase.setString(2, _couponID);
            addPurchase.setString(3, _serial);
            return addPurchase.execute();
        }
        catch(SQLException e){
            return false;
        }
    }

    @Override
    public int getAmountOfPurchasedCoupons(String couponID) {

        CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call amount_of_coupons_purchased(?)");
            cs.setString(1, couponID);
            ResultSet rs = cs.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException e){
            return 0;
        }
    }


    public Connection getConnection()
    {
        return dbConnect.getConnection();
    }

    public ResultSet getAllCoupons(){
       /* CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call get_all_coupons");
            ResultSet rs = cs.executeQuery();
            return rs;
        }
        catch(SQLException e){
            return null;
        }*/
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID,Name FROM coupon");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("TKT", e.toString());
            return null;
        }

    }
    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType)
    {
        java.sql.CallableStatement addUser;
        try
        {
            addUser = con.prepareCall("call add_systemuser (?,?,?,?,?,?,?,?)");
            addUser.setString(1, _UserName);
            addUser.setString(2, _ID);
            addUser.setString(3, _Password);
            addUser.setString(4, _FirstName);
            addUser.setString(5, _LastName);
            addUser.setString(6, _Phone);
            addUser.setString(7, _EMail);
            addUser.setString(8, _UserType);
            addUser.execute();
        }
        catch (SQLException e)
        {
        }
    }
    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail, String _Gender, Date _DateOfBirth)
    {
        CallableStatement addUser;
        try
        {
            addUser = con.prepareCall("call add_systemuser (?,?,?,?,?,?,?,?)");
            addUser.setString(1, _UserName);
            addUser.setString(2, _ID);
            addUser.setString(3, _Password);
            addUser.setString(4, _FirstName);
            addUser.setString(5, _LastName);
            addUser.setString(6, _Phone);
            addUser.setString(7, _EMail);
            addUser.setString(8, "Client");
            addUser.execute();
            CallableStatement addClient;
            addClient = con.prepareCall("call add_client (?,?,?)");
            addClient.setString(1, _UserName);
            addClient.setString(2, _Gender);
            addClient.setDate(3, _DateOfBirth);
            addClient.execute();
        }
        catch (SQLException e)
        {
            Log.d("", "");
        }
    }
}
