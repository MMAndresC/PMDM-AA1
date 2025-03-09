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
import com.svalero.tournaments.contract.TournamentModifyContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.fragment.MapFragment;
import com.svalero.tournaments.interfaces.OnCoordinatesUpdatedListener;
import com.svalero.tournaments.presenter.TournamentAddPresenter;
import com.svalero.tournaments.presenter.TournamentModifyPresenter;
import com.svalero.tournaments.util.DateUtil;
import com.svalero.tournaments.util.ValidateUtil;

import java.util.ArrayList;

public class TournamentFormView extends AppCompatActivity implements OnCoordinatesUpdatedListener, TournamentAddContract.View, TournamentModifyContract.View {

    private String action;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_form_view);

        Intent intent = getIntent();
        Tournament tournament = intent.getParcelableExtra("tournament");
        ArrayList<Tournament> tournamentList = new ArrayList<>();
        if(tournament != null) {
            action = "modify";
            id = tournament.getId();
            loadData(tournament); // Fill EditTexts with data
            tournamentList.add(tournament);
        } else action = "add";
        createMapFragment(tournamentList);
    }

    private void createMapFragment(ArrayList<Tournament> tournamentList){
        String width = "match_parent";
        String height = "200dp";
        double defaultFocusLong = -0.87734;
        double defaultFocusLat = 41.65606;
        MapFragment mapFragment = MapFragment.newInstance(
                tournamentList,
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
        Tournament tournament = ValidateUtil.validateTournamentForm(findViewById(R.id.containerLogin));
        if(tournament.getName() == null) {
            String message = getString(R.string.missing_fields);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }
        if(action.equals("add")) {
            TournamentAddContract.Presenter presenter = new TournamentAddPresenter(this);
            presenter.saveTournament(tournament);
        }else if(action.equals("modify")){
            TournamentModifyContract.Presenter presenter = new TournamentModifyPresenter(this);
            presenter.modifyTournament(tournament, id);
        }
        action = "";
        changeActivity();
    }

    @Override
    public void onCoordinatesUpdated(double longitude, double latitude) {
        String textLongitude = String.valueOf(longitude);
        String textLatitude = String.valueOf(latitude);
        ((TextView) findViewById(R.id.editTournamentLong)).setText(textLongitude);
        ((TextView) findViewById(R.id.editTournamentLat)).setText(textLatitude);
    }

    @Override
    public void updatedTournament(Tournament tournament) {
        action = "";
        changeActivity();
    }

    @Override
    public void showErrorMessage(String message) {
        action = "";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        action = "";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadData(Tournament tournament){
        ((EditText) findViewById(R.id.editTournamentName)).setText(tournament.getName());
        ((EditText) findViewById(R.id.editTournamentManager)).setText(tournament.getManager());
        String initDate = DateUtil.formatFromString(tournament.getInitDate(),"dd/MM/yyyy", "yyyy-MM-dd");
        ((EditText) findViewById(R.id.editTournamentStart)).setText(initDate);
        String endDate = DateUtil.formatFromString(tournament.getEndDate(),"dd/MM/yyyy", "yyyy-MM-dd");
        ((EditText) findViewById(R.id.editTournamentEnd)).setText(endDate);
        String address = tournament.getAddress();
        if(!address.isBlank()){
            ((EditText) findViewById(R.id.editTournamentCity)).setText(address.split(",")[0]);
            ((EditText) findViewById(R.id.editTournamentCountry)).setText(address.split(",")[1]);
        }
        ((EditText) findViewById(R.id.editTournamentPrize)).setText(String.valueOf(tournament.getPrize()));
        ((TextView) findViewById(R.id.editTournamentLong)).setText(String.valueOf(tournament.getLongitude()));
        ((TextView) findViewById(R.id.editTournamentLat)).setText(String.valueOf(tournament.getLatitude()));
    }

    private void changeActivity(){
        //Redirection to list view
        Intent intent = new Intent(this, TournamentsListView.class);
        startActivity(intent);
        //Not let come back with back button
        finish();
    }
}