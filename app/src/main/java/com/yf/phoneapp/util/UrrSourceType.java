package com.yf.phoneapp.util;

/**
 * Created by lishanfeng on 16/8/16.
 */
public class UrrSourceType {

    public static OnUrrSourceType onUrrSourceType;

    public static void setOnUrrSourceType(OnUrrSourceType type){
        onUrrSourceType=type;
    }

   public interface OnUrrSourceType{
       void OnurrSourceTypeListener(int type);
   }

}
