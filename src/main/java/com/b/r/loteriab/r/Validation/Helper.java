package com.b.r.loteriab.r.Validation;

import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static String createToken (int number) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[number];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    public  static String getSystemTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss a");
        return format.format(calendar.getTime());
    }

    public  static String getTimeFromDate(Date date, String shift){
        if (date == null){
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat format;
        if (shift.equals("12")){
            format = new SimpleDateFormat("HH:mm:ss a");
        }else {
            format = new SimpleDateFormat("HH:mm:ss");
        }
        return format.format(calendar.getTime());
    }

    public static Date addDays(Date date, int numberDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, numberDays);
        return cal.getTime();
    }

    public static Date setTimeToDate(Date date, String []time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
        cal.set(Calendar.MINUTE,Integer.parseInt(time[1]));
        cal.set(Calendar.SECOND,Integer.parseInt(time[2]));
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
}
