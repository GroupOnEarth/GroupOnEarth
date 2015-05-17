package com.grouponearth.avi.grouponearth.DataLayer;

/**
 * Created by Avi on 01/05/2015.
 */

import android.database.sqlite.SQLiteQuery;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
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
    public ResultSet getUserCloseExpCoupons(String userName, Date date) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_close_date_purchased_coupons (?,?)");
            cs.setString(1, userName);
            cs.setDate(2,date );
            return cs.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }

    public void updateCouponInfo(String couponID,String couponName,String desc,String category,int originalPrice, int discountPrice, Date expirationDate)
    {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call update_coupon_by_id(?,?,?,?,?,?,?)");
            cs.setString(1, couponID);
            cs.setString(2, couponName);
            cs.setString(3, desc);
            cs.setString(4, category);
            cs.setInt(5, originalPrice);
            cs.setInt(6, discountPrice);
            cs.setDate(7, expirationDate);
            cs.execute();
        }
        catch(SQLException e){
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

    /**
     *
     * @param searchName is a coupon name (or part of a name) represented as a string to search for
     * @return a ResultSet of coupons matching by name to searchName
     */
    public ResultSet searchCouponsByName(String searchName){
        CallableStatement cs;
        try
        {
            cs = con.prepareCall("call search_coupons_by_name (?)");
            cs.setString(1, searchName);
            return cs.executeQuery();
        }
        catch(SQLException e)
        {
            Log.d("TKT", "sql exception", e);
            return null;
        }
    }

    public ResultSet searchCouponsByNameAndCategory(String searchName, String category){
        CallableStatement cs;
        try
        {
            cs = con.prepareCall("call search_coupons_by_name_and_category (?, ?)");
            cs.setString(1, searchName);
            cs.setString(2, category);
            return cs.executeQuery();
        }
        catch(Exception e)
        {
            Log.d("TKT", "sql exception", e);
            return null;
        }
    }

    public ResultSet getClientCoupons(String userName){
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_client_purchases (?)");
            cs.setString(1, userName);
            return cs.executeQuery();
        }
        catch(SQLException e){
            return null;
        }
    }



    public String getPasswordByMail(String mailAddress) {

        CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call get_password_by_mail(?)");
            cs.setString(1, mailAddress);
            ResultSet rs = cs.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException e){
            return null;
        }
    }


    @Override
    public ResultSet sendQuery(String query) {
        try {
            Statement st = con.createStatement();
            return st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }


    public ResultSet isMailExists(String mailAddress) {
        CallableStatement getMail;
        try
        {
            getMail = con.prepareCall("call get_user_by_mail (?)");
            getMail.setString(1, mailAddress);
            return getMail.executeQuery();
        }
        catch (SQLException e)
        {
            Log.d("", "");
            return null;
        }
    }
    public ResultSet isUserExists(String userName){
        CallableStatement getUser;
        try
        {
            getUser = con.prepareCall("call get_user (?)");
            getUser.setString(1, userName);
            return getUser.executeQuery();
        }
        catch (SQLException e)
        {
            Log.d("", "");
            return null;
        }
    }


    public void addBusiness(String _UserName, String businessName,String address,String desc,double longitude,double latitude)
    {
        CallableStatement addBusiness;
        try
        {
            addBusiness = con.prepareCall("call add_business (?,?,?,?,?,?)");
            addBusiness.setString(1, businessName);
            addBusiness.setString(2, address);
            addBusiness.setString(3, desc);
            addBusiness.setString(4, _UserName);
            addBusiness.setDouble(5, longitude);
            addBusiness.setDouble(6, latitude);
            addBusiness.execute();
        }
        catch (SQLException e)
        {
        }
    }

    public void updateClientLocation(String userName, double longitude, double latitude)
    {
        CallableStatement updateCL;
        try
        {
            updateCL = con.prepareCall("call update_client_location (?,?,?)");
            updateCL.setString(1, userName);
            updateCL.setDouble(2, longitude);
            updateCL.setDouble(3, latitude);
            updateCL.execute();
        }
        catch (SQLException e)
        {
        }
    }

    public ResultSet getCouponsByDistance(double longitude, double latitude, int distance)
    {
        CallableStatement getCBD;
        try
        {
            getCBD = con.prepareCall("call get_coupon_by_distance (?,?,?)");
            getCBD.setDouble(1, longitude);
            getCBD.setDouble(2, latitude);
            getCBD.setInt(3, distance);
            return getCBD.executeQuery();
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    @Override
    public ResultSet getRatingInfo(String couponID) {
        CallableStatement cs;
        try
        {
            cs = con.prepareCall("call get_rating_info (?)");
            cs.setString(1, couponID);
            ResultSet rs =  cs.executeQuery();
            return rs;
        }
        catch (SQLException e)
        {
            Log.d("", "");
            return null;
        }
        /*String query = "SELECT Rating, NumOfRatings FROM coupon WHERE coupon.ID="+couponID;
        Statement st;
        try{
            st = con.createStatement();
            return st.executeQuery(query);
        }
        catch (SQLException e){
            return null;
        }*/
    }


    @Override
    public void updateCouponRating(String couponID, double newRating) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call update_Coupon_Rating(?,?)");
            cs.setString(1, couponID);
            cs.setDouble(2, newRating);
            cs.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String getUserByMail(String mail)
    {
        CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call get_user_by_mail(?)");
            cs.setString(1, mail);
            ResultSet rs = cs.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException e){
            return null;
        }
    }

    @Override
    public ResultSet getCouponsByPreferences(String userName, String searchName) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_coupons_by_preferences (?,?)");
            cs.setString(1, userName);
            cs.setString(2, searchName);
            return cs.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }

    @Override
    public void addCoupon(String id, String name, String desc, String category, int price,int dPrice, Date experationDate, String businessName) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call add_coupon (?,?,?,?,?,?,?,?)");
            cs.setString(1, id);
            cs.setString(2, name);
            cs.setString(3, desc);
            cs.setString(4, category);
            cs.setInt(5, price);
            cs.setInt(6, dPrice);
            cs.setDate(7, experationDate);
            cs.setString(8, businessName);
            cs.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBusinessName(String userName) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_business_name(?)");
            cs.setString(1, userName);
            ResultSet rs =  cs.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch (SQLException e){
            return null;
        }
    }

    @Override
    public ResultSet getBusinessByName(String businessName) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_business_by_name(?)");
            cs.setString(1, businessName);
            return cs.executeQuery();
        }
        catch(SQLException e){
            return null;
        }
    }

    @Override
    public ResultSet getBusinessCoupons(String businessName) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_business_coupons(?)");
            cs.setString(1, businessName);
            return cs.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }

    @Override
    public int getAmountOfFulfilledCoupons(String couponID) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call amount_of_coupons_fulfilled(?)");
            cs.setString(1, couponID);
            ResultSet rs = cs.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException e){
            return 0;
        }
    }

    public ResultSet getUnapprovedCoupons()
    {
        CallableStatement getUAC;
        try
        {
            getUAC = con.prepareCall("call get_unapproved_coupons ()");
            return getUAC.executeQuery();
        }
        catch (SQLException e)
        {
            return null;
        }
    }
    public void approveCoupon(String couponID)
    {
        CallableStatement approveCoupon;
        try
        {
            approveCoupon = con.prepareCall("call approve_coupon (?)");
            approveCoupon.setString(1, couponID);
            approveCoupon.execute();
        }
        catch (SQLException e)
        {
        }
    }
    public void deleteCoupon(String couponID)
    {
        CallableStatement deleteCoupon;
        try
        {
            deleteCoupon = con.prepareCall("call delete_coupon (?)");
            deleteCoupon.setString(1, couponID);
            deleteCoupon.execute();
        }
        catch (SQLException e)
        {
        }
    }
    public String getMailByUser(String userName)
    {
        CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call get_mail_by_username(?)");
            cs.setString(1, userName);
            ResultSet rs = cs.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException e){
            return null;
        }
    }
    public ResultSet getUserByUserName(String userName)
    {
        CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call get_user(?)");
            cs.setString(1, userName);
            return  cs.executeQuery();
        }
        catch(SQLException e){
            return null;
        }
    }
    public ResultSet getClientByUserName(String userName)
    {
        CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call get_client_info(?)");
            cs.setString(1, userName);
            return  cs.executeQuery();
        }
        catch(SQLException e){
            return null;
        }
    }
    public void updateClientInfo(String userName,String password,String firstName,String lastName,String phone)
    {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call update_client_info(?,?,?,?,?)");
            cs.setString(1, userName);
            cs.setString(2, password);
            cs.setString(3, firstName);
            cs.setString(4, lastName);
            cs.setString(5, phone);
            cs.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public ResultSet getClientUsers()
    {
        CallableStatement getCU;
        try
        {
            getCU = (CallableStatement) con.prepareCall("call get_client_users ()");
            return getCU.executeQuery();
        }
        catch (SQLException e)
        {
            return null;
        }
    }
    public String getMailByBusinessName(String businessName)
    {
        CallableStatement cs;
        try{
            cs = (CallableStatement) con.prepareCall("call get_mail_by_businessName(?)");
            cs.setString(1, businessName);
            ResultSet rs = cs.executeQuery();
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException e){
            return null;
        }
    }

    @Override
    public void fulfillCoupon(String serialNumber) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call set_purchase_to_fulfilled(?)");
            cs.setString(1, serialNumber);
            cs.executeQuery();
        }
        catch (SQLException e){

        }
    }

    @Override
    public ResultSet getUnfulfilledCoupon(String serialNumber) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_unfulfilled_purchase_by_serial_number(?)");
            cs.setString(1, serialNumber);
            return cs.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }

    @Override
    public void updateBusinessProfile(String businessName, String newAddress, String newDesc, double longitude, double latitude) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call update_business_profile(?,?,?,?,?)");
            cs.setString(1, businessName);
            cs.setString(2, newAddress);
            cs.setString(3, newDesc);
            cs.setDouble(4, longitude);
            cs.setDouble(5, latitude);
            cs.executeQuery();
        }
        catch (SQLException e){

        }
    }

    @Override
    public ResultSet getAllBusinesses() {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_all_businesses()");
            return cs.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }

    @Override
    public ResultSet getCouponsByBusiness(String businessName, String query) {
        CallableStatement cs;
        try{
            cs = con.prepareCall("call get_coupons_by_business_and_query(?,?)");
            cs.setString(1, businessName);
            cs.setString(2, query);
            return cs.executeQuery();
        }
        catch (SQLException e){
            return null;
        }
    }


}
