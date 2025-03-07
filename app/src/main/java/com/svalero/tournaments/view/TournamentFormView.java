package com.svalero.tournaments.view;

import android.content.Intent;
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
import com.svalero.tournaments.util.DateUtil;

import java.text.ParseException;
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
        Tournament tournament = validateTournamentForm();
        if(tournament.getName() == null) {
            String message = getString(R.string.missing_fields);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }
        TournamentAddContract.Presenter presenter = new TournamentAddPresenter(this);
        presenter.saveTournament(tournament);
        //Redirection to list view
        Intent intent = new Intent(this, TournamentsListView.class);
        startActivity(intent);
        //Not let come back with back button
        finish();
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

    public Tournament validateTournamentForm() throws NullPointerException, NumberFormatException{
        try {
            String name = ((EditText) findViewById(R.id.editTournamentName)).getText().toString();
            String manager = ((EditText) findViewById(R.id.editTournamentManager)).getText().toString();
            String city = ((EditText) findViewById(R.id.editTournamentCity)).getText().toString();
            String country = ((EditText) findViewById(R.id.editTournamentCountry)).getText().toString();
            String initDate = ((EditText) findViewById(R.id.editTournamentStart)).getText().toString();
            String endDate = ((EditText) findViewById(R.id.editTournamentEnd)).getText().toString();
            float prize = Float.parseFloat(((EditText) findViewById(R.id.editTournamentPrize)).getText().toString());
            double longitude = Double.parseDouble(((TextView) findViewById(R.id.editTournamentLong)).getText().toString());
            double latitude = Double.parseDouble(((TextView) findViewById(R.id.editTournamentLat)).getText().toString());

            if (name.isBlank() || manager.isBlank() || city.isBlank() || country.isBlank() || initDate.isBlank() || endDate.isBlank())
                return new Tournament();
            initDate = initDate.replace("/", "-");
            initDate = DateUtil.formatFromString(initDate, "yyyy-MM-dd", "dd-MM-yyyy");
            endDate = endDate.replace("/", "-");
            endDate = DateUtil.formatFromString(endDate, "yyyy-MM-dd", "dd-MM-yyyy");
            if(initDate.isBlank() || endDate.isBlank()) return new Tournament();
            if(!DateUtil.checkDates(initDate, endDate)) return new Tournament();

            String address = city + ", " + country;
            return new Tournament(name, initDate, endDate, prize, address, manager, latitude, longitude);
        } catch (NumberFormatException | NullPointerException | ParseException e) {
            return new Tournament();
        }
    }
}