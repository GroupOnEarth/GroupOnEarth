package com.grouponearth.avi.grouponearth.BusinessLayer;

import java.io.Serializable;

/**
 * Created by WIN8 on 30/04/2015.
 */
public interface IBL{

    /**
     *
     * @param userName is the user name to be confirmed
     * @param password is the user's password that is to be confirmed
     * @return the USERTYPE of the confirmed user or an empty string if user does not exist
     */
    public String confirmLogin(String userName, String password);

    public void test();
}
