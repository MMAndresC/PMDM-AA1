package com.svalero.tournaments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.TournamentWinnersContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.domain.TournamentWinners;
import com.svalero.tournaments.fragment.MapFragment;
import com.svalero.tournaments.presenter.TournamentWinnersPresenter;
import com.svalero.tournaments.util.DateUtil;
import com.svalero.tournaments.util.ParseUtil;
import com.svalero.tournaments.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;

public class TournamentDetailView extends AppCompatActivity implements TournamentWinnersContract.View {

    private TournamentWinners tournamentWinner;
    private TournamentWinnersContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_detail_view);
        Intent intent = getIntent();
        long tournamentId = loadDetailData(intent);
        if(tournamentId != -1){
            presenter = new TournamentWinnersPresenter(this, tournamentId);
            presenter.loadTournamentWinners();
        }

    }

    private long loadDetailData(Intent intent){
        long id = intent.getLongExtra("id", -1);
        String name = extractParam(intent, "name");
        ((TextView) findViewById(R.id.nameDetailTournament)).setText(name);
        ((TextView) findViewById(R.id.managerDetailTournament)).setText(extractParam(intent, "manager"));
        ((TextView) findViewById(R.id.addressDetailTournament)).setText(extractParam(intent, "address"));
        ((TextView) findViewById(R.id.dateDetailTournament)).setText(formatDate(intent));
        String prize = intent.getFloatExtra("prize", 0) + " €";
        ((TextView) findViewById(R.id.prizeDetailTournament)).setText(prize);
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);
        createMapFragment(longitude, latitude, name);
        return id;
    }

    private void createMapFragment(double longitude, double latitude, String name){
        String width = "150dp";
        String height = "150dp";
        Tournament tournament = new Tournament();
        tournament.setLatitude(latitude);
        tournament.setLongitude(longitude);
        tournament.setName(name);
        ArrayList<Tournament> tournamentsList = new ArrayList<>();
        tournamentsList.add(tournament);
        MapFragment mapFragment = MapFragment.newInstance(
                tournamentsList,
                longitude,
                latitude,
                width,
                height
        );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMapDetail, mapFragment);
        transaction.commitNow();
    }

    private String formatDate(Intent intent){
        String initDate = extractParam(intent, "initDate");
        String endDate = extractParam(intent, "endDate");
        return DateUtil.formatFromString(initDate, "dd-MM-yyyy") + " / "
                + DateUtil.formatFromString(endDate, "dd-MM-yyyy");
    }

    private String extractParam(Intent intent, String name){
        String param = intent.getStringExtra(name);
        if(param == null) param = "N/D";
        return param;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemTournaments){
            Intent intent = new Intent(this, TournamentsListView.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void listTournamentWinners(List<TournamentWinners> winnersList) {
        tournamentWinner = SearchUtil.findTournamentWinner(winnersList);
        ((TextView) findViewById(R.id.nameTeamDetailTournament)).setText(tournamentWinner.getNameTeam());
        //Select image with team name
        ImageView imageView = findViewById(R.id.logoTeamDetailTournament);
        String imageName = ParseUtil.parseImageName(tournamentWinner.getNameTeam());
        if(imageName != null) {
            int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if(resID != 0) imageView.setImageResource(resID);
            else imageView.setImageResource(R.drawable.no_photos);
        } else imageView.setImageResource(R.drawable.no_photos);
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