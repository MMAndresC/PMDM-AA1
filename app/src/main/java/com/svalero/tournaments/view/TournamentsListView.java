package com.svalero.tournaments.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.TournamentsListAdapter;
import com.svalero.tournaments.contract.TournamentRemoveContract;
import com.svalero.tournaments.contract.TournamentsListContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.presenter.TournamentRemovePresenter;
import com.svalero.tournaments.presenter.TournamentsListPresenter;

import java.util.ArrayList;
import java.util.List;


public class TournamentsListView extends AppCompatActivity implements TournamentsListContract.View, TournamentRemoveContract.View {

    private List<Tournament> tournamentsList;
    private TournamentsListAdapter adapter;
    private TournamentsListContract.Presenter presenter;
    private Tournament selectedTournament;
    private TournamentRemoveContract.Presenter presenterRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments_list_view);

        presenter = new TournamentsListPresenter(this);
        presenter.loadTournaments();

        presenterRemove = new TournamentRemovePresenter(this);

        tournamentsList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.listTournamentsRecycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TournamentsListAdapter(tournamentsList, this);
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

    //Register context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_context_menu_tournament, menu);
    }

    //Check selected option in context menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //WITH SWITCH NOT WORK
        if (id == R.id.editMenuTournament) {

            return true;
        } else if (id == R.id.removeMenuTournament) {
            createDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void toDelete(){
        if (selectedTournament != null) {
            String token = getToken();
            if(token == null){
                String message = "Required user logged in delete actions";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                return;
            }
            token = "Bearer " + token;
            presenterRemove.removeTournament(token, selectedTournament.getId());
        }
    }

    //Create confirm dialog to delete tournament
    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.auth_remove)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               toDelete();
                            }})
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
        builder.create().show();
    }

    private String getToken(){
        SharedPreferences sharedPreferences = getSharedPreferences("AppTournament", MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    public void onClickAddTournament(View view){
        Intent intent = new Intent(this, TournamentFormView.class);
        startActivity(intent);
    }

    public void setSelectedTournament(Tournament tournament) {
        this.selectedTournament = tournament;
    }

    @Override
    public void listTournaments(List<Tournament> tournamentsList) {
        this.tournamentsList.clear();
        this.tournamentsList.addAll(tournamentsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deletedTournament(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        tournamentsList.remove(selectedTournament);
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