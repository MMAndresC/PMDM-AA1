package com.svalero.tournaments.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.MainContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.presenter.MainPresenter;
import com.svalero.tournaments.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

public class MainView extends AppCompatActivity implements MainContract.View, Style.OnStyleLoaded{

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;

    private List<Tournament> tournamentsList;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.loadTournaments();

        tournamentsList = new ArrayList<>();

        mapView = findViewById(R.id.mainMap);
        //Load map and callback when is loaded to show markers
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS,this);
        pointAnnotationManager = MapUtil.initializePointAnnotationManager(mapView);

    }

    //Add menu action_bar_main in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        return true;
    }

    @Override
    public void listTournaments(List<Tournament> tournamentsList) {
        this.tournamentsList.addAll(tournamentsList);
        drawMap();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {

    }

    //Methods to draw map with markers
    private void drawMap(){
        for(Tournament tournament : this.tournamentsList) {
            addMarker(tournament.getName(), tournament.getLongitude(), tournament.getLatitude());
        }
    }
    private void addMarker(String message, float longitude, float latitude) {
        PointAnnotationOptions marker = new PointAnnotationOptions()
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker))
                .withTextField(message)
                .withPoint(Point.fromLngLat(longitude, latitude));
        pointAnnotationManager.create(marker);
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {
       drawMap();
    }
}