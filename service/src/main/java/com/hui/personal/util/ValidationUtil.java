package com.hui.personal.util;

import com.hui.personal.WebServiceConstants;
import org.apache.commons.lang3.StringUtils;
import java.util.Calendar;

public class ValidationUtil implements WebServiceConstants{
    public static boolean isValidName(String name){
        return StringUtils.isNotEmpty(name)
                && name.length() > 2
                && name.length() < 20;
    }
    public static boolean isValidPhoneNum(String phoneNum) {
        return StringUtils.isNotEmpty(phoneNum)
                && phoneNum.matches("\\d{11}");
    }
    public static int checkOrderInfo(String name, String phoneNum, Calendar dateTime){
        return !DateUtil.withinTwoDays(dateTime) ? afterTwoDays : !(isValidName(name) && isValidPhoneNum(phoneNum)) ?  invalidInput : success;
    }
}
