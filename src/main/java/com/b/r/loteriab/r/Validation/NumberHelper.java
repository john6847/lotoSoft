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

public class NumberHelper {

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static Object getNumericValue(Object value) {
        if (value == null) {
            return 0;
        }

        return value;
    }
}
