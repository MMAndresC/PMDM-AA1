package com.svalero.tournaments.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.TournamentAddContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.fragment.MapFragment;
import com.svalero.tournaments.interfaces.OnCoordinatesUpdatedListener;
import com.svalero.tournaments.presenter.TournamentAddPresenter;
import com.svalero.tournaments.util.ValidateUtil;

import java.util.ArrayList;

public class TournamentFormView extends AppCompatActivity implements OnCoordinatesUpdatedListener, TournamentAddContract.View {

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
        double defaultFocusLong = -0.87734;
        double defaultFocusLat = 41.65606;
        MapFragment mapFragment = MapFragment.newInstance(
                new ArrayList<>(),
                defaultFocusLong,
                defaultFocusLat,
                width,
                height
        );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMapForm, mapFragment);
        transaction.commitNow();
    }

    public void onClickSaveTournament(View view){
        Tournament tournament = ValidateUtil.validateTournamentForm(view);
        if(tournament.getName() == null) {
            String message = getString(R.string.missing_fields);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }
        TournamentAddContract.Presenter presenter = new TournamentAddPresenter(this);
        presenter.saveTournament(tournament);
    }

    @Override
    public void onCoordinatesUpdated(double longitude, double latitude) {
        String textLongitude = String.valueOf(longitude);
        String textLatitude = String.valueOf(latitude);
        ((TextView) findViewById(R.id.editTournamentLong)).setText(textLongitude);
        ((TextView) findViewById(R.id.editTournamentLat)).setText(textLatitude);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}