package com.grouponearth.avi.grouponearth.BusinessLayer;

/**
 * Created by Avi on 01/05/2015.
 */
import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;

/**
 * Created by WIN8 on 30/04/2015.
 */
public interface IBL {

    /**
     * @param userName is the user name to be confirmed
     * @param password is the user's password that is to be confirmed
     * @return the USERTYPE of the confirmed user or an empty string if user does not exist
     */
    public String confirmLogin(String userName, String password);

    //Add Methods

    public boolean addPurchase(String userName, String couponID, int alreadyBought);

    // Get Methods
    public ResultSet getCouponByID(String couponID);


    public int getAmountOfPurchasedCoupons(String couponID);
    public ResultSet getAllCoupons();
    public String getPasswordByMail(String mailAddress);

    // Is Exists Methods
    public boolean isMailExists(String mailAddress);


    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);
    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail, String _Gender, Date _DateOfBirth);


}