package com.svalero.tournaments.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ParseUtil {

    public static String parseImageName(String imageName){
        if(imageName != null) {
            imageName = imageName.toLowerCase().trim();
            imageName = imageName.replaceAll("\\.", "_");
            imageName = imageName.replaceAll("\\s+", "_");
        }
        return imageName;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] imageData) {
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
    }
}
