package com.svalero.tournaments.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String formatFromString(String stringDate, String outFormat) {
        //Convert to LocalDate to validate date
        DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, inFormat);

        // Change it to new format
        DateTimeFormatter changedFormat = DateTimeFormatter.ofPattern(outFormat);
        return date.format(changedFormat);
    }
}
