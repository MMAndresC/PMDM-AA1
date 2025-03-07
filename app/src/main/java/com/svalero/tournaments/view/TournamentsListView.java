package com.svalero.tournaments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.TournamentsListAdapter;
import com.svalero.tournaments.contract.TournamentsListContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.presenter.TournamentsListPresenter;

import java.util.ArrayList;
import java.util.List;


public class TournamentsListView extends AppCompatActivity implements TournamentsListContract.View {

    private List<Tournament> tournamentsList;
    private TournamentsListAdapter adapter;
    private TournamentsListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments_list_view);

        presenter = new TournamentsListPresenter(this);
        presenter.loadTournaments();

        tournamentsList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.listTournamentsRecycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TournamentsListAdapter(tournamentsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemTournaments){
            Intent intent = new Intent(this, TournamentsListView.class);
            startActivity(intent);
        }
        return true;
    }

    //
    public void onClickAddTournament(View view){
        Intent intent = new Intent(this, TournamentFormView.class);
        startActivity(intent);
    }

    @Override
    public void listTournaments(List<Tournament> tournamentsList) {
        this.tournamentsList.addAll(tournamentsList);
        adapter.notifyDataSetChanged();
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