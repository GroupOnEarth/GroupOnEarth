package com.grouponearth.avi.grouponearth.DataLayer;

import java.sql.Date;
import java.sql.ResultSet;

/**
 * Created by Avi on 30/04/2015.
 */
public interface IDAL {



    public ResultSet getSystemUsers();
    public ResultSet getUser(String _UserName);
    public ResultSet getClient(String _UserName);
    public ResultSet getCouponByID(String couponID);

    /**
     * Add new Purchase to the database.
     *
     * @param _userName
     * @param _couponID- coupon to be purchased
     *
     * @pre: @param _UserName and @param _couponID exists in database.
     * @post: couponpurchases.size = @pre couponpurchases.size+1
     */
    public boolean addPurchase(String _userName, String _couponID, String _serial);

    public int getAmountOfPurchasedCoupons(String couponID);

    public ResultSet getAllCoupons();

    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);

    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail, String _Gender, Date _DateOfBirth);

}
