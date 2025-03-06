package com.svalero.tournaments.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.svalero.tournaments.R;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.fragment.MapFragment;
import com.svalero.tournaments.interfaces.OnCoordinatesUpdatedListener;

import java.util.ArrayList;

public class TournamentFormView extends AppCompatActivity implements OnCoordinatesUpdatedListener {

    private final double DEFAULT_FOCUS_LAT = 41.65606;
    private final double DEFAULT_FOCUS_LONG = -0.87734;
    private Tournament newTournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_form_view);

        createMapFragment();
    }

    private void createMapFragment(){
        String width = "match_parent";
        String height = "200dp";
        MapFragment mapFragment = MapFragment.newInstance(
                new ArrayList<>(),
                DEFAULT_FOCUS_LONG,
                DEFAULT_FOCUS_LAT,
                width,
                height
        );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMapForm, mapFragment);
        transaction.commitNow();
    }

    public void onClickSaveTournament(View view){

    }

    private boolean validateData(){

        return false;
    }

    @Override
    public void onCoordinatesUpdated(double longitude, double latitude) {
        String textLongitude = "Long: " + longitude + "/ ";
        String textLatitude = "Lat: " + latitude;
        ((TextView) findViewById(R.id.editTournamentLong)).setText(textLongitude);
        ((TextView) findViewById(R.id.editTournamentLat)).setText(textLatitude);
    }
}