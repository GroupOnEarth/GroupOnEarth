package com.grouponearth.avi.grouponearth.BusinessLayer;

/**
 * Created by Avi on 01/05/2015.
 */
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

    public void addBusiness(String _UserName, String businessName,String address,String desc,double longitude,double latitude);
    public boolean addPurchase(String userName, String couponID, int alreadyBought);

    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail,String _UserType);
    public void addSystemUser(String _UserName, String _ID,String _Password,String _FirstName,String _LastName,String _Phone,String _EMail, String _Gender, Date _DateOfBirth);


    // Get Methods
    public ResultSet getCouponByID(String couponID);
    public ResultSet getCouponsByName(String searchName);
    public ResultSet getCouponsByNameAndCategory(String searchName, String category);
    public ResultSet getClientCoupons(String userName);
    public ResultSet getCouponsByPreferences(String userName, String searchName);
    public ResultSet getBusinessByName(String businessName);


    public int getAmountOfPurchasedCoupons(String couponID);
    public ResultSet getAllCoupons();
    public String getPasswordByMail(String mailAddress);

    // Is Exists Methods
    public boolean isMailExists(String mailAddress);
    public boolean isUserExists(String userName);
    public boolean isCouponExists(String couponID);

    public ResultSet getInformation(String query);

    public void updateClientLocation(String userName, double longitude, double latitude);

    public ResultSet getCouponsByDistance(double longitude, double latitude, int distance);

    public ResultSet getCouponRatingInfo(String couponID);

    public void updateCouponRatingInfo(String couponID, double newRating);

    public String getUserByMail(String mail);

    public void addCoupon(String id, String name, String desc, String category, int price, int dPrice, Date experationDate, String businessName);

    public String getBusinessName(String userName);

    public ResultSet getBusinessCoupons(String businessName);

    public int getAmountOfFulfilledCoupons(String couponID);

    public void sendMail(String username, String password, String mail);
    public ResultSet getUnapprovedCoupons();
    public void approveCoupon(String couponID);
    public void deleteCoupon(String couponID);
    public boolean isUnapprovedExists();
    public void sendPurchasedMail(String username, String couponID, int alreadyBought, String couponName, String couponDesc);
    public ResultSet getUserByUserName(String userName);
    public ResultSet getClientByUserName(String userName);
    public void updateClientInfo(String userName,String password,String firstName,String lastName,String phone);
    public ResultSet getClientUsers();
    public void sendApprovedMail(String businessName, String couponID, String couponName);
    public void sendUnApprovedMail(String businessName, String couponID, String couponName);
    public boolean existUserCloseExpCoupons(String userName);
    public void fulfillCoupon(String serialNumber);

    public boolean isPurchaseFulfilled(String serialNumber);

    public void updateBusinessProfile(String businessName, String newAddress, String newDesc, double longitude, double latitude);

    public ResultSet getAllBusinesses();

    public ResultSet getCouponsByBusiness(String businessName, String query);

    public void updateCouponInfo(String couponID,String couponName,String desc,String category,int originalPrice, int discountPrice, Date expirationDate);
}