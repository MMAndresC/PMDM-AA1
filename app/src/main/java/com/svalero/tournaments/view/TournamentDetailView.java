package com.svalero.tournaments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.svalero.tournaments.R;
import com.svalero.tournaments.contract.TournamentWinnersContract;
import com.svalero.tournaments.domain.TournamentWinners;
import com.svalero.tournaments.presenter.TournamentWinnersPresenter;
import com.svalero.tournaments.util.DateUtil;
import com.svalero.tournaments.util.SearchUtil;

import java.util.List;

public class TournamentDetailView extends AppCompatActivity implements TournamentWinnersContract.View {

    private TournamentWinners tournamentWinner;
    private TournamentWinnersContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_detail);
        Intent intent = getIntent();
        long tournamentId = loadDetailData(intent);
        if(tournamentId != -1){
            presenter = new TournamentWinnersPresenter(this, tournamentId);
            presenter.loadTournamentWinners();
        }

    }

    private long loadDetailData(Intent intent){
        //TODO con el id del torneo mandar la consulta para encontrar al winner
        long id = intent.getLongExtra("id", -1);
        ((TextView) findViewById(R.id.nameDetailTournament)).setText(extractParam(intent, "name"));
        ((TextView) findViewById(R.id.managerDetailTournament)).setText(extractParam(intent, "manager"));
        ((TextView) findViewById(R.id.addressDetailTournament)).setText(extractParam(intent, "address"));
        ((TextView) findViewById(R.id.dateDetailTournament)).setText(formatDate(intent));
        String prize = intent.getFloatExtra("prize", 0) + " €";
        ((TextView) findViewById(R.id.prizeDetailTournament)).setText(prize);
        float latitude = intent.getFloatExtra("latitude", 0);
        float longitude = intent.getFloatExtra("longitude", 0);
        //TODO pasarle al mapa las coordenadas, el valor por defecto centrarlo en Zgz
        return id;
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
        //TODO poner imagen del logo del equipo
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