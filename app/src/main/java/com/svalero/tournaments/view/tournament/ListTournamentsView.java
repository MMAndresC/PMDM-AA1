package com.svalero.tournaments.view.tournament;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.TournamentsListAdapter;
import com.svalero.tournaments.contract.tournament.RemoveTournamentContract;
import com.svalero.tournaments.contract.tournament.ListTournamentsContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.presenter.tournament.RemoveTournamentPresenter;
import com.svalero.tournaments.presenter.tournament.ListTournamentPresenter;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.util.SortUtil;

import java.util.ArrayList;
import java.util.List;


public class ListTournamentsView extends AppCompatActivity implements ListTournamentsContract.View, RemoveTournamentContract.View {

    private List<Tournament> tournamentsList;
    private TournamentsListAdapter adapter;
    private Tournament selectedTournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments_list_view);

        ListTournamentsContract.Presenter presenter = new ListTournamentPresenter(this);
        presenter.loadTournaments();

        tournamentsList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.listTournamentsRecycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TournamentsListAdapter(tournamentsList, this);
        recyclerView.setAdapter(adapter);
    }

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemTournaments){
            Intent intent = new Intent(this, ListTournamentsView.class);
            startActivity(intent);
        }
        return true;
    }*/

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
            sendToModify();
            return true;
        } else if (id == R.id.removeMenuTournament) {
            createDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void sendToModify(){
        if (selectedTournament != null) {
            Intent intent = new Intent(this, FormTournamentView.class);
            intent.putExtra("tournament", selectedTournament);
            startActivity(intent);
        }
    }

    private void toDelete(){
        RemoveTournamentContract.Presenter presenterRemove = new RemoveTournamentPresenter(this);
        if (selectedTournament != null) {
            String token = SharedPreferencesUtil.getCustomSharedPreferences(this, "token");
            if(token == null){
                String message = getString(R.string.required_user_logged);
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
        builder.setMessage(R.string.auth_remove_tournament)
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

    public void onClickAddTournament(View view){
        Intent intent = new Intent(this, FormTournamentView.class);
        startActivity(intent);
    }

    public void setSelectedTournament(Tournament tournament) {
        this.selectedTournament = tournament;
    }

    @Override
    public void deletedTournament(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        tournamentsList.remove(selectedTournament);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void listTournaments(List<Tournament> tournamentsList) {
        boolean oldestFirst = ((RadioButton) findViewById(R.id.orderOlder)).isChecked();
        this.tournamentsList.clear();
        this.tournamentsList.addAll(tournamentsList);
        SortUtil.sortTournamnentsByDate(this.tournamentsList, oldestFirst);
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

    public void onClickSortOptions(View view){
        boolean oldestFirst = ((RadioButton) findViewById(R.id.orderOlder)).isChecked();
        SortUtil.sortTournamnentsByDate(this.tournamentsList, oldestFirst);
        adapter.notifyDataSetChanged();
    }
}