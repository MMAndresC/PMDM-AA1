package com.svalero.tournaments.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.MainAdapter;
import com.svalero.tournaments.contract.TournamentsListContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.presenter.TournamentsListPresenter;
import com.svalero.tournaments.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

public class MainView extends AppCompatActivity implements TournamentsListContract.View{

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private List<Tournament> tournamentsList;
    private TournamentsListContract.Presenter presenter;
    private boolean loadedMap = false;
    private boolean loadedMarkers = false;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new TournamentsListPresenter(this);
        presenter.loadTournaments();

        tournamentsList = new ArrayList<>();

        RecyclerView nextTournamentsView = findViewById(R.id.nextTournamentsRecycler);
        //To put scroll in component
        nextTournamentsView.hasFixedSize();
        //Set inner layout type
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        nextTournamentsView.setLayoutManager(linearLayoutManager);

        //Link data with adapter and adapter with recycleView
        mainAdapter = new MainAdapter(tournamentsList);
        nextTournamentsView.setAdapter(mainAdapter);

        mapView = findViewById(R.id.mainMap);
        //Load map and callback when is loaded to show markers if not showed before
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS,style -> {
            loadedMap = true;
            if(!loadedMarkers && !tournamentsList.isEmpty())
                drawMap();
        });
        pointAnnotationManager = MapUtil.initializePointAnnotationManager(mapView);
    }

    //Add menu action_bar_main in activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        return true;
    }

    // Select option in menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemTournaments){
            Intent intent = new Intent(this, TournamentsListView.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void listTournaments(List<Tournament> tournamentsList) {
        this.tournamentsList.addAll(tournamentsList);
        mainAdapter.notifyDataSetChanged();
        if(loadedMap) drawMap();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Methods to draw map with markers
    private void drawMap(){
        loadedMarkers = true;
        //Set focus & zoom
        CameraOptions cameraOptions = MapUtil.setCameraOptions();
        mapView.getMapboxMap().setCamera(cameraOptions);

        for(Tournament tournament : this.tournamentsList) {
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