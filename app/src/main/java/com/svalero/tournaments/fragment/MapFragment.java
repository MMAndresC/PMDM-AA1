package com.svalero.tournaments.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.interfaces.OnCoordinatesUpdatedListener;
import com.svalero.tournaments.util.MapUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapClickListener {

    private static final String ARG_PARAM1 = "tournamentsList";
    private static final String ARG_PARAM2 = "focusLong";
    private static final String ARG_PARAM3 = "focusLat";
    private static final String ARG_PARAM4 = "height";
    private static final String ARG_PARAM5 = "width";
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private ArrayList<Tournament> tournamentsList = new ArrayList<>();
    private double focusLong;
    private double focusLat;
    private String width;
    private String height;
    private OnCoordinatesUpdatedListener callback;
    private GesturesPlugin gesturesPlugin;
    private PointAnnotation currentPoint;


    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance(
            ArrayList<Tournament> tournamentsList,
            double focusLong,
            double focusLat,
            String width,
            String height
    ) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, tournamentsList);
        args.putDouble(ARG_PARAM2, focusLong);
        args.putDouble(ARG_PARAM3, focusLat);
        args.putString(ARG_PARAM4, height);
        args.putString(ARG_PARAM5, width);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.mainMap);

        if (getArguments() != null) {
            tournamentsList = getArguments().getParcelableArrayList(ARG_PARAM1);
            focusLong = getArguments().getDouble(ARG_PARAM2);
            focusLat = getArguments().getDouble(ARG_PARAM3);
            height = getArguments().getString(ARG_PARAM4);
            width = getArguments().getString(ARG_PARAM5);
        }

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
            drawMap();
            if (callback != null) initializeGesturesPlugin();
        });
        pointAnnotationManager = MapUtil.initializePointAnnotationManager(mapView);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCoordinatesUpdatedListener) {
            callback = (OnCoordinatesUpdatedListener) context;
        }
    }

    private void drawMap(){
        //Set focus & zoom
        CameraOptions cameraOptions = MapUtil.setCameraOptions(focusLong, focusLat);
        mapView.getMapboxMap().setCamera(cameraOptions);

        //Set width & height map
        setMapSize();

        for(Tournament tournament : tournamentsList) {
            addMarker(tournament.getName(), tournament.getLongitude(), tournament.getLatitude());
        }
    }

    private void addMarker(String message, double longitude, double latitude) {
        //Resize icon adjusting it to zoom
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 30, 50, false);

        PointAnnotationOptions markerOptions = MapUtil.createMarker(longitude, latitude, resizedBitmap, message);
        currentPoint = pointAnnotationManager.create(markerOptions);
    }

    public void setMapSize() {
        if (mapView != null) {
            ViewGroup.LayoutParams params = mapView.getLayoutParams();

            if(width == null) return;

            if (width.equals("match_parent")) {
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                params.width = MapUtil.dpToPx(Integer.parseInt(width.replace("dp", "")));
            }

            if(height == null) return;

           if (height.equals("wrap_content")) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = MapUtil.dpToPx(Integer.parseInt(height.replace("dp", "")));
            }

            mapView.setLayoutParams(params);
        }
    }

    private void initializeGesturesPlugin() {
        gesturesPlugin = GesturesUtils.getGestures(mapView);
        gesturesPlugin.addOnMapClickListener(this);
    }

    @Override
    public boolean onMapClick(@NonNull Point point) {
        if (currentPoint != null) {
            pointAnnotationManager.delete(currentPoint);
        }
        addMarker("", point.longitude(), point.latitude());
        callback.onCoordinatesUpdated(point.longitude(), point.latitude());
        return false;
    }
}