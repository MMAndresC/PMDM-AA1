package com.svalero.tournaments.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.util.MapUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private static final String ARG_PARAM1 = "tournamentsList";
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private ArrayList<Tournament> tournamentsList = new ArrayList<>();


    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance(ArrayList<Tournament> tournamentsList) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, tournamentsList);
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
            tournamentsList = (ArrayList<Tournament>) getArguments().getSerializable(ARG_PARAM1);
        }

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> drawMap());
        pointAnnotationManager = MapUtil.initializePointAnnotationManager(mapView);
        return view;
    }

    private void drawMap(){
        //Set focus & zoom
        CameraOptions cameraOptions = MapUtil.setCameraOptions();
        mapView.getMapboxMap().setCamera(cameraOptions);

        for(Tournament tournament : tournamentsList) {
            addMarker(tournament.getName(), tournament.getLongitude(), tournament.getLatitude());
        }
    }

    private void addMarker(String message, float longitude, float latitude) {
        //Resize icon adjusting it to zoom
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 30, 50, false);

        PointAnnotationOptions marker = MapUtil.createMarker(longitude, latitude, resizedBitmap, message);
        pointAnnotationManager.create(marker);
    }
}