package com.svalero.tournaments.util;

public class ConstantsUtil {

    public static String translateRegionToString(int region){
        switch(region){
            case 1: return "North America";
            case 2: return "South America";
            case 3: return "Europe";
            case 4: return "Asia";
            case 5: return "Oceania";
        }
        return "N/D";
    }
}
