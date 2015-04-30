package com.grouponearth.avi.grouponearth.DataLayer;

import java.sql.Date;

/**
 * Created by Avi on 29/04/2015.
 */
public class SqlQueries {


    public String add_business(String name, String address, String description, String businessManager){
        String query = "INSERT INTO `group_on_earth_db`.`business`" +
                "(`Name`,`Address`,`Description`,`BusinessManager_UserName`)VALUES("+name+","+address+", "+description+","+businessManager+")";
        return query;
    }
    public String add_client(String userName, String gender, int gpsLocation, Date dateOfBirth){
        String query = "INSERT INTO `group_on_earth_db`.`client` (`UserName`, `Gender`, `GPSLocation`, `DateOfBirth`) VALUES("+userName+","+gender+","+ gpsLocation+", "+dateOfBirth+")";
        return query;
    }

    public String add_client_interests(String userName, String interest){
        String query = "INSERT INTO `group_on_earth_db`.`clientinterests`(`UserName`, `Interest`) VALUES ("+userName+", "+interest+")";
        return query;
    }

    public String add_client_preference(String userName, String preference){
        String query = "INSERT INTO `group_on_earth_db`.`clientpreferences`" +
                "(`UserName`," +
                "`Preference`)" +
                "VALUES(" +
                 userName+"," +
                 preference+")";
        return query;
    }

   /* public String add_coupon(String ID, String name, String description, String category, String origPrice, String discountPrice, Date expDate, int isApproved){
        String query = "INSERT INTO `group_on_earth_db`.`coupon`(`ID`,`Name`,`Description`,`Category`,`OriginalPrice`,`DiscountPrice`,`ExperationDate`,`isApproved`)"+
                "VALUES ("+ID+", "+name+", "+description+", "+category+", "+origPrice+", "+discountPrice+", "+ expDate+" ,"
    }*/





}


