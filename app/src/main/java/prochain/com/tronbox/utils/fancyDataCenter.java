package prochain.com.tronbox.utils;

//import com.snappydb.DB;
//import com.snappydb.DBFactory;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.tron.walletcli.WalletApiWrapper;

import java.util.ArrayList;

/**
 * 功能说明：
 *
 * @author: alex
 * @version: 1.0
 * @date: 16/10/8
 * @email:
 * @Copyright (c) 2016. All rights reserved.
 */
public class fancyDataCenter {







    public  WalletApiWrapper walletApi;



    public String DEVICE_CLIENT_ID = "device_client_id";
    public String USER_TRON_ADDRESS = "user_tron_address";
    public String USER_TRON_FILE_PATH = "user_tron_filepath";

    ///end of key


    private static fancyDataCenter ourInstance = new fancyDataCenter();


    public static fancyDataCenter getInstance() {
        return ourInstance;
    }

    private fancyDataCenter() {


        new Thread() {
            public void run() {
                initData();
            }
        }.start();
    }



    public void initData()
    {
        Context context =  fancyApplication.getInstance().getApplicationContext();
        walletApi = null;


    }












    public String getTronAddress()
    {
        Context context =  fancyApplication.getInstance().getApplicationContext();
        String tronAddress =    (String)fancyObjSaveUtil.readObject(context,USER_TRON_ADDRESS);

        if (tronAddress==null)
        {
            tronAddress = "";
        }
        return tronAddress;

    }

    public void setTronAddress(String tronAddress)
    {
        Context context =  fancyApplication.getInstance().getApplicationContext();
        fancyObjSaveUtil.saveObject(context,tronAddress,USER_TRON_ADDRESS);

    }



    public String getCurrentTronPath()
    {
        Context context =  fancyApplication.getInstance().getApplicationContext();
        String filepath =    (String)fancyObjSaveUtil.readObject(context,USER_TRON_FILE_PATH);

        if (filepath==null)
        {
            filepath = "";
        }
        return filepath;
    }



    public void setCurrentTronPath(String filepath)
    {
        Context context =  fancyApplication.getInstance().getApplicationContext();
        fancyObjSaveUtil.saveObject(context,filepath,USER_TRON_FILE_PATH);

    }








}
