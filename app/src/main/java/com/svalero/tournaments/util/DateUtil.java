package com.svalero.tournaments.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatFromString(String stringDate, String outFormat, String inPattern) throws DateTimeParseException {
        try {
            //Convert to LocalDate to validate date
            DateTimeFormatter inFormat = DateTimeFormatter.ofPattern(inPattern);
            LocalDate date = LocalDate.parse(stringDate, inFormat);

            // Change it to new format
            DateTimeFormatter changedFormat = DateTimeFormatter.ofPattern(outFormat);
            return date.format(changedFormat);
        }catch(DateTimeParseException e){
            return "";
        }
    }

    public static boolean checkDates(String initDate, String endDate) throws ParseException {
        try{
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date init = inputFormat.parse(initDate);
            Date end = inputFormat.parse(endDate);
            return init.before(end);
        }catch(ParseException e){
            return false;
        }
    }
}
