package com.svalero.tournaments.util;

import com.svalero.tournaments.domain.Tournament;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SortUtil {

    public static void sortTournamnentsByDate(List<Tournament> tournamentsList, boolean oldestFirst){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Collections.sort(tournamentsList, new Comparator<Tournament>() {
            @Override
            public int compare(Tournament t1, Tournament t2) {
                try {
                    Date date1 = dateFormat.parse(t1.getInitDate());
                    Date date2 = dateFormat.parse(t2.getInitDate());
                    return oldestFirst ? date1.compareTo(date2) : date2.compareTo(date1);
                } catch (ParseException e) {
                    throw new RuntimeException("Error parsing date", e);
                }
            }
        });
        //return tournamentsList;
    }
}
