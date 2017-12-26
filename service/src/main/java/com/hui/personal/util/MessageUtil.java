package com.hui.personal.util;


import com.hui.personal.WebServiceConstants;

public class MessageUtil implements WebServiceConstants{
    private static String getResposne(int msgId, boolean ifOrder){
        String msg = "";
        switch (msgId){
            case outOfTwoDays : msg = outOfTwoDaysMsg;break;
            case invalidInput : msg = invalidInputMsg;break;
            case saleOut : msg = saleOutMsg;break;
            case success : msg = successMsg;break;
            case duplicated : msg = duplicatedMsg;break;
            case badDateFormat : msg = badDateFormatMsg;break;
            case notFound : msg = notFoundMsg;break;
            case successCancel : msg = successCancelMsg;break;
        };
        return ifOrder ? "{orderResponse:{status:\"" + msgId + "\",message:\"" + msg + "}}"
                : "{cancelResponse:{status:\"" + msgId + "\",message:\"" + msg + "}}";
    }

    public static String getCancelResponse(int msgId){
        return getResposne(msgId,true);
    }
    public static String getOrderResponse(int msgId){
        return getResposne(msgId,false);
    }
}
