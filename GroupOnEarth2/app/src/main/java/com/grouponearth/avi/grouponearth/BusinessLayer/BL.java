package com.grouponearth.avi.grouponearth.BusinessLayer;

/**
 * Created by Avi on 01/05/2015.
 */
import android.util.Log;

import com.grouponearth.avi.grouponearth.DataLayer.IDAL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by Avi on 30/04/2015.
 */
public class BL implements IBL {
    private static IDAL dal;

    public BL()
    {
    }

    public BL(IDAL dal)
    {
        this.dal=dal;
    }

    public String confirmLogin(String userName, String password) {
        ResultSet rs = dal.getUser(userName);

        try {
            if(rs.next()){
                String dbUName = rs.getString(1);
                String dbPass = rs.getString(3);
                if(userName.equals(dbUName) & password.equals(dbPass)){
                    return rs.getString(8);
                }
                else return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public ResultSet getCouponByID(String couponID) {
        return dal.getCouponByID(couponID);
    }


    public boolean addPurchase(String userName, String couponID, int alreadyBought) {
        String serial = couponID + "-" + alreadyBought;
        return dal.addPurchase(userName, couponID, serial);
    }

    @Override
    public int getAmountOfPurchasedCoupons(String couponID) {
        return dal.getAmountOfPurchasedCoupons(couponID);
    }
    public ResultSet getCouponsByName(String searchName)
    {
        return dal.searchCouponsByName(searchName);
    }
    public ResultSet getCouponsByNameAndCategory(String searchName, String category){
        return dal.searchCouponsByNameAndCategory(searchName, category);
    }

    public ResultSet getClientCoupons(String userName){
        return dal.getClientCoupons(userName);
    }

    @Override
    public ResultSet getCouponsByPreferences(String userName, String searchName) {
        return dal.getCouponsByPreferences(userName, searchName);
    }

    @Override
    public ResultSet getBusinessByName(String businessName) {
        return dal.getBusinessByName(businessName);
    }

    public ResultSet getAllCoupons(){
        return dal.getAllCoupons();
    }

    public void addSystemUser(String _UserName, String _ID, String _Password, String _FirstName, String _LastName, String _Phone, String _EMail, String _UserType)
    {
        dal.addSystemUser( _UserName,_ID,_Password,_FirstName,_LastName,_Phone,_EMail,_UserType);
    }
    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail, String _Gender, Date _DateOfBirth)
    {
        dal.addSystemUser( _UserName, _ID, _Password, _FirstName, _LastName, _Phone, _EMail, _Gender, _DateOfBirth);
    }

    public String getPasswordByMail(String mailAddress)
    {
        return dal.getPasswordByMail(mailAddress);
    }
    public boolean isMailExists(String mailAddress)
    {
        ResultSet rs = dal.isMailExists(mailAddress);
        int count = 0;
        try
        {
            rs.last();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            count = rs.getRow();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (count==0)
            return false;
        else
            return true;
    }
    public boolean isUserExists(String userName)
    {
        ResultSet rs =dal.isUserExists(userName);
        int count = 0;
        try
        {
            rs.last();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            count = rs.getRow();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (count==0)
            return false;
        else
            return true;
    }

    public boolean isCouponExists(String couponID){
        ResultSet rs = dal.getCouponByID(couponID);
        int count = 0;
        try
        {
            rs.last();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            count = rs.getRow();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (count==0)
            return false;
        else
            return true;
    }


    public ResultSet getInformation(String query)
    {
        return dal.sendQuery(query);
    }

    public void addBusiness(String _UserName, String businessName,String address,String desc,double longitude,double latitude)
    {
        dal.addBusiness( _UserName,  businessName, address, desc, longitude, latitude);
    }

    public void updateClientLocation(String userName, double longitude, double latitude)
    {
        dal.updateClientLocation(userName, longitude, latitude);
    }

    public ResultSet getCouponsByDistance(double longitude, double latitude, int distance)
    {
        return dal.getCouponsByDistance(longitude, latitude, distance);
    }

    @Override
    public ResultSet getCouponRatingInfo(String couponID) {
        return dal.getRatingInfo(couponID);
    }

    @Override
    public void updateCouponRatingInfo(String couponID, double newRating) {
        dal.updateCouponRating(couponID,newRating);
    }

    public String getUserByMail(String mail)
    {
        return dal.getUserByMail(mail);
    }

    public void addCoupon(String id, String name, String desc, String category, int price, int dPrice, Date experationDate, String businessName) {
        dal.addCoupon(id, name, desc, category, price,dPrice, experationDate, businessName);
    }

    @Override
    public String getBusinessName(String userName) {
        return dal.getBusinessName(userName);
    }

    @Override
    public ResultSet getBusinessCoupons(String businessName) {
        return dal.getBusinessCoupons(businessName);
    }

    @Override
    public int getAmountOfFulfilledCoupons(String couponID) {
        return dal.getAmountOfFulfilledCoupons(couponID);
    }

    public void sendMail(String username, String password, String mail)
    {
        try {
            GmailSender sender = new GmailSender("grouponearth@gmail.com", "grouponearth1");
            sender.sendMail("GroupOnEarth- Password Recovery",
                    "\nThis is Password Recovery message as you requested.\n\nUserName: " + username + "\nPassword: " + password + "\n\nKeep Shopping for fun!",
                    "GroupOnEarth",
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
    public ResultSet getUnapprovedCoupons()
    {
        return dal.getUnapprovedCoupons();
    }
    public void approveCoupon(String couponID)
    {
        dal.approveCoupon(couponID);
    }
    public void deleteCoupon(String couponID)
    {
        dal.deleteCoupon(couponID);
    }
    public boolean isUnapprovedExists()
    {
        ResultSet rs = dal.getUnapprovedCoupons();
        int count = 0;
        try
        {
            rs.last();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            count = rs.getRow();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (count==0)
            return false;
        else
            return true;
    }
    public void sendPurchasedMail(String username, String couponID, int alreadyBought, String couponName, String couponDesc)
    {
        String serial = couponID + "-" + alreadyBought;
        String mail= dal.getMailByUser(username);
        try {
            GmailSender sender = new GmailSender("grouponearth@gmail.com", "grouponearth1");
            sender.sendMail("GroupOnEarth- Coupon Purchased",
                    "\nThank you for purchase from GroupOnEarth.\n\nCoupon: " + couponName + "\n" + couponDesc +"\n\nCoupon Code: " + serial + "\n\nKeep Shopping for fun!",
                    "GroupOnEarth",
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
    public ResultSet getUserByUserName(String userName)
    {
        return dal.getUserByUserName(userName);
    }
    public ResultSet getClientByUserName(String userName)
    {
        return dal.getClientByUserName(userName);
    }
    public void updateClientInfo(String userName,String password,String firstName,String lastName,String phone)
    {
        dal.updateClientInfo(userName,password,firstName,lastName,phone);
    }
    public ResultSet getClientUsers()
    {
        return dal.getClientUsers();
    }
    public void sendApprovedMail(String businessName, String couponID, String couponName)
    {
        String mail= dal.getMailByBusinessName(businessName);
        try {
            GmailSender sender = new GmailSender("grouponearth@gmail.com", "grouponearth1");
            sender.sendMail("GroupOnEarth- Coupon Approval",
                    "\nHello.\n\nCoupon: " + couponName + "\nCoupon ID: " + couponID +"\n\nWas Approved and will be open for purchasing.\n\nIt's time to make some money.\nRemember: More coupons will get you more buyers.",
                    "GroupOnEarth",
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
    public void sendUnApprovedMail(String businessName, String couponID, String couponName)
    {
        String mail= dal.getMailByBusinessName(businessName);
        try {
            GmailSender sender = new GmailSender("grouponearth@gmail.com", "grouponearth1");
            sender.sendMail("GroupOnEarth- Coupon Denied",
                    "\nHello.\n\nCoupon: " + couponName + "\nCoupon ID: " + couponID +"\n\nWas not approved by admin and will be deleted from the system.\n\nSorry.\nRemember: More coupons will get you more buyers.",
                    "GroupOnEarth",
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
    public boolean existUserCloseExpCoupons(String userName) {
        Date date = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        ResultSet rs = dal.getUserCloseExpCoupons(userName, date);
        int count = 0;
        try {
            rs.last();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            count = rs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count <= 0)
            return true;
        else
            return false;
    }
    @Override
    public void fulfillCoupon(String serialNumber) {
        dal.fulfillCoupon(serialNumber);
    }

    @Override
    public boolean isPurchaseFulfilled(String serialNumber) {
        ResultSet rs = dal.getUnfulfilledCoupon(serialNumber);
        int count = 0;
        try
        {
            rs.last();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            count = rs.getRow();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if (count<=0)
            return true;
        else
            return false;
    }

    @Override
    public void updateBusinessProfile(String businessName, String newAddress, String newDesc, double longitude, double latitude) {
        dal.updateBusinessProfile(businessName, newAddress, newDesc, longitude, latitude);
    }

    @Override
    public ResultSet getAllBusinesses() {
        return dal.getAllBusinesses();
    }

    @Override
    public ResultSet getCouponsByBusiness(String businessName, String query) {
        return dal.getCouponsByBusiness(businessName, query);
    }
    public void updateCouponInfo(String couponID,String couponName,String desc,String category,int originalPrice, int discountPrice, Date expirationDate)
    {
        dal.updateCouponInfo(couponID,couponName,desc,category,originalPrice, discountPrice, expirationDate);
    }
}