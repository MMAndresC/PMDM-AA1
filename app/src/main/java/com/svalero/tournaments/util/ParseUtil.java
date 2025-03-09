package com.svalero.tournaments.util;

public class ParseUtil {

    public static String parseImageName(String imageName){
        if(imageName != null) {
            imageName = imageName.toLowerCase().trim();
            imageName = imageName.replaceAll("\\.", "_");
            imageName = imageName.replaceAll("\\s+", "_");
        }
        return imageName;
    }
}
