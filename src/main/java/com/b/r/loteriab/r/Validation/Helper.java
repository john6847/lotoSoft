package com.b.r.loteriab.r.Validation;

import org.javatuples.Pair;

import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
            format = new SimpleDateFormat("hh:mm:ss a");
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


    public  static int getDateValueFromDate (Date date, int type) {
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (type){
            case 0:
                return localDate.getDayOfMonth();
                case 1:
                return localDate.getMonthValue();
                case 2:
                return localDate.getYear();
        }
        return 0;
    }

    public static int getTimeValueFromDate(Date date, int type){
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        calendar.get(Calendar.HOUR);        // gets hour in 12h format
        switch (type){
            case 0:
                return calendar.get(Calendar.MINUTE);
            case 1:
                return calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format;
            case 2:
                return calendar.get(Calendar.HOUR); // gets hour in 12h format;
        }
        return 0;
    }

    public static Date setTimeToDate(Date date, String []time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
        cal.set(Calendar.MINUTE,Integer.parseInt(time[1]));
        cal.set(Calendar.SECOND,Integer.parseInt("00"));
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
    public static Pair<Date, Date> getStartDateAndEndDate (String start, String end, Date dayToFind, int dayToSubstract, String format){

        Date startDate = Helper.parseDate(start, format);
        Date endDate = Helper.parseDate(end, format);

        String [] timeStart = Helper.getTimeFromDate(startDate, "").split(":");
        Date resultStartDate = Helper.setTimeToDate(dayToFind, timeStart);
        if (dayToSubstract < 0){
            resultStartDate = Helper.addDays(resultStartDate, -1);
        }
        String [] timeEnd = Helper.getTimeFromDate(endDate, "").split(":");
        Date resultEndDate = Helper.setTimeToDate(dayToFind, timeEnd);

        return  Pair.with(resultStartDate, resultEndDate);
    }

    public static Date parseDate(String date, String format){
        Date newDate = new Date();
        try {
            newDate = new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static Date getCloseDateTime(String closeTime, Date dateToApply){
        Date date = Helper.convertStringToDate(closeTime, "dd/MM/yyyy, hh:mm:ss a");
        String [] time = Helper.getTimeFromDate(date, "").split(":");
        return Helper.setTimeToDate(dateToApply, time);
    }

    public static Date convertStringToDate(String sDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(sDate);
            System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
