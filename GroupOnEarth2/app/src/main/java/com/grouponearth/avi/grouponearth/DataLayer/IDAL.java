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

    public ResultSet getClientCoupons(String userName);

    public ResultSet getAllCoupons();

    public ResultSet searchCouponsByName(String searchName);

    public ResultSet searchCouponsByNameAndCategory(String searchName, String category);

    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);

    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail, String _Gender, Date _DateOfBirth);


    public String getPasswordByMail(String mailAddress);



    public ResultSet sendQuery(String query);

    public void addBusiness(String _UserName, String businessName,String address,String desc,double longitude,double latitude);


    public ResultSet isMailExists(String mailAddress);
    public ResultSet isUserExists(String userName);

    public void updateClientLocation(String userName, double longitude, double latitude);
    public ResultSet getCouponsByDistance(double longitude, double latitude, int distance);

    public ResultSet getRatingInfo(String couponID);

    public void updateCouponRating(String couponID, double newRating);

    public String getUserByMail(String mail);

    public ResultSet getCouponsByPreferences(String userName, String searchName);


    public void addCoupon(String id, String name, String desc, String category, int price,int dPrice, Date experationDate, String businessName);

    public String getBusinessName(String userName);

    public ResultSet getBusinessByName(String businessName);

    public ResultSet getBusinessCoupons(String businessName);

    public int getAmountOfFulfilledCoupons(String couponID);

    public ResultSet getUnapprovedCoupons();
    public void approveCoupon(String couponID);
    public void deleteCoupon(String couponID);
    public String getMailByUser(String userName);
    public ResultSet getUserByUserName(String userName);
    public ResultSet getClientByUserName(String userName);
    public void updateClientInfo(String userName,String password,String firstName,String lastName,String phone);
    public ResultSet getClientUsers();
    public String getMailByBusinessName(String businessName);
    public ResultSet getUserCloseExpCoupons(String userName, Date date);
    public void fulfillCoupon(String serialNumber);

    public ResultSet getUnfulfilledCoupon(String serialNumber);

    public void updateBusinessProfile(String businessName, String newAddress, String newDesc, double longitude, double latitude);

    public ResultSet getAllBusinesses();

    public ResultSet getCouponsByBusiness(String businessName, String query);

    public void updateCouponInfo(String couponID,String couponName,String desc,String category,int originalPrice, int discountPrice, Date expirationDate);
}
