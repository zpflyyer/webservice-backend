package com.hui.personal;
public interface WebServiceConstants {
    // constant
    public static final String pvParentPath = "C:\\Users\\15710\\WebProjects\\webservice-backend\\service\\src\\main\\resources";
    public static final String suffix = ".ticfile";
    public static final int seatsPerTrip = 100;
    public static final int flushThreshold = 10000;
    public static final String standardFormat = "yyyy-MM-dd,HH:mm";
    public static final String snapFormat = "yyyyMMddHHmm";

    //status code
    public static final int badDateFormat = -4;
    public static final int afterTwoDays = -3;
    public static final int invalidInput = -2;
    public static final int saleOut = -1;
    public static final int success = 0;
    public static final int duplicated = 1;

    //message
    public static final String badDateFormatMsg = "date format is invalid";
    public static final String afterTwoDaysMsg = "you can only order ticket for today or tommorow.";
    public static final String invalidInputMsg = "invalid name or phone number.";
    public static final String saleOutMsg = "Sorry, tickets for this time are sold out, try another time.";
    public static final String successMsg = "Successfully ordered!";
    public static final String duplicatedMsg = "This phone number has ordered a ticket for this time!";
}
