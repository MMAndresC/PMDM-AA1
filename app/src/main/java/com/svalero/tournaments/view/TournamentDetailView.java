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
import com.svalero.tournaments.contract.tournament.TournamentWinnersContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.domain.TournamentWinners;
import com.svalero.tournaments.fragment.MapFragment;
import com.svalero.tournaments.presenter.TournamentWinnersPresenter;
import com.svalero.tournaments.util.DateUtil;
import com.svalero.tournaments.util.ParseUtil;
import com.svalero.tournaments.util.SearchUtil;
import com.svalero.tournaments.view.tournament.ListTournamentsView;

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
        Tournament tournament = intent.getParcelableExtra("tournament");
        if(tournament == null){
            Toast.makeText(this, "Tournament null", Toast.LENGTH_LONG).show();
            Intent comeBack = new Intent(this, ListTournamentsView.class);
            startActivity(comeBack);
            finish();
            return -1;
        }
        long id = tournament.getId();
        String name = tournament.getName();
        ((TextView) findViewById(R.id.nameDetailTournament)).setText(name);
        ((TextView) findViewById(R.id.managerDetailTournament)).setText(extractParam(tournament.getManager()));
        ((TextView) findViewById(R.id.addressDetailTournament)).setText(extractParam(tournament.getAddress()));
        ((TextView) findViewById(R.id.dateDetailTournament)).setText(formatDate(tournament.getInitDate(), tournament.getEndDate()));
        String prize = tournament.getPrize() + " €";
        ((TextView) findViewById(R.id.prizeDetailTournament)).setText(prize);
        double longitude = tournament.getLongitude();
        double latitude = tournament.getLatitude();
        createMapFragment(longitude, latitude, name);
        return id;
    }

    private void createMapFragment(double longitude, double latitude, String name){
        String width = "match_parent";
        String height = "200dp";
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

    private String formatDate(String initDate, String endDate){
        initDate = extractParam(initDate);
        endDate = extractParam(endDate);
        return DateUtil.formatFromString(initDate, "dd-MM-yyyy", "yyyy-MM-dd") + " / "
                + DateUtil.formatFromString(endDate, "dd-MM-yyyy", "yyyy-MM-dd");
    }

    private String extractParam(String param){
        if(param == null) param = "N/D";
        return param;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemTournaments){
            Intent intent = new Intent(this, ListTournamentsView.class);
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