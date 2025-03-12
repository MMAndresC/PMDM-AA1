package com.svalero.tournaments.view.team;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.svalero.tournaments.adapter.TeamsListAdapter;
import com.svalero.tournaments.contract.team.ListTeamsContract;
import com.svalero.tournaments.contract.team.ModifyTeamContract;
import com.svalero.tournaments.contract.team.RemoveTeamContract;
import com.svalero.tournaments.contract.tournament.RemoveTournamentContract;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.presenter.team.ListTeamsPresenter;
import com.svalero.tournaments.presenter.team.RemoveTeamPresenter;
import com.svalero.tournaments.presenter.tournament.RemoveTournamentPresenter;
import com.svalero.tournaments.util.SharedPreferencesUtil;
import com.svalero.tournaments.view.tournament.FormTournamentView;

import java.util.ArrayList;
import java.util.List;

public class ListTeamsView extends AppCompatActivity implements ListTeamsContract.View, RemoveTeamContract.View {

    private Team selectedTeam;
    private TeamsListAdapter adapter;
    private List<Team> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_list_view);

        ListTeamsContract.Presenter presenter = new ListTeamsPresenter(this);
        presenter.loadTeams();

        teamList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.listTeamsRecycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TeamsListAdapter(teamList,this);
        recyclerView.setAdapter(adapter);
    }

    //Register context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_context_menu_team, menu);
    }

    //Check selected option in context menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //WITH SWITCH NOT WORK
        if (id == R.id.editMenuTeam) {
            sendToModify();
            return true;
        } else if (id == R.id.removeMenuTeam) {
            createDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    //Create confirm dialog to delete tournament
    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.auth_remove_team)
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

    private void toDelete(){
        RemoveTeamContract.Presenter presenterRemove = new RemoveTeamPresenter(this);
        if (selectedTeam != null) {
            String token = SharedPreferencesUtil.getCustomSharedPreferences(this, "token");
            if(token == null){
                String message = getString(R.string.required_user_logged);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                return;
            }
            token = "Bearer " + token;
            presenterRemove.removeTeam(token, selectedTeam.getId());
        }
    }

    private void sendToModify(){
        if (selectedTeam != null) {
            Intent intent = new Intent(this, FormTeamView.class);
            intent.putExtra("team", selectedTeam);
            startActivity(intent);
        }
    }

    @Override
    public void listTeams(List<Team> teamsList) {
        this.teamList.clear();
        this.teamList.addAll(teamsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deletedTeam(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        teamList.remove(selectedTeam);
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

    public void onClickAddTeam(View view){
        Intent intent = new Intent(this, FormTeamView.class);
        startActivity(intent);
    }

    public void setSelectedTeam(Team team){
        this.selectedTeam = team;
    }
}