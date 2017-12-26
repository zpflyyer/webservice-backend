package com.hui.personal.util;


import com.hui.personal.WebServiceConstants;

public class MessageUtil implements WebServiceConstants{
    public static String getResponse(int msgId){
        String msg = "";
        switch (msgId){
            case afterTwoDays : msg = afterTwoDaysMsg;break;
            case invalidInput : msg = invalidInputMsg;break;
            case saleOut : msg = saleOutMsg;break;
            case success : msg = successMsg;break;
            case duplicated : msg = duplicatedMsg;break;
            case badDateFormat: msg = badDateFormatMsg;break;
        };
        return "{orderResponse:{status:\"" + msgId + "\",message:\"" + msg + "}}";
    }
}
