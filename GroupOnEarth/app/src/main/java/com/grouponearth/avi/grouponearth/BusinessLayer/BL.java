package com.grouponearth.avi.grouponearth.BusinessLayer;

/**
 * Created by Avi on 30/04/2015.
 */
public class BL implements IBL {
    @Override
    public String confirmLogin(String userName, String password) {
        return "Admin";
    }
}
