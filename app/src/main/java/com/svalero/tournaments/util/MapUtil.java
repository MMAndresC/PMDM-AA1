package com.svalero.tournaments.util;

import android.graphics.Bitmap;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

public class MapUtil {
    public static PointAnnotationManager initializePointAnnotationManager(MapView mapView) {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        return PointAnnotationManagerKt.createPointAnnotationManager(
                annotationPlugin, annotationConfig);

    }

    public static CameraOptions setCameraOptions(){
        return new CameraOptions.Builder()
            .center(Point.fromLngLat(-0.87734, 41.65606))
            .zoom(2.0)
            .build();
    }

    public static PointAnnotationOptions createMarker(
            double longitude,
            double latitude,
            Bitmap image,
            String message
    ){
        return new PointAnnotationOptions()
                .withIconImage(image)
                .withTextField(message)
                .withTextSize(4.0f)
                .withTextColor("black")
                .withTextHaloColor("white")
                .withTextHaloWidth(1.0f)
                .withPoint(Point.fromLngLat(longitude, latitude));
    }
}
