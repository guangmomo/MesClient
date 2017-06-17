package com.xuliwen.mestest2.file.sp;


import android.content.Context;
import android.content.SharedPreferences;

import com.xuliwen.App;


public class SpUtil {

    private static final String SP_NAME = "com.tjz.sp";
    private static final String SP_KEY = "key";

    public static void put( String value){
        SharedPreferences sp = App.getContext().getSharedPreferences(SP_NAME,Context.MODE_MULTI_PROCESS);
        sp.edit().putString(SP_KEY,value).commit();
    }

    public static String get(){
        SharedPreferences sp =App.getContext().getSharedPreferences(SP_NAME,Context.MODE_MULTI_PROCESS);
        return sp.getString(SP_KEY,null);
    }
}