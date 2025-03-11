package com.svalero.tournaments.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tournaments.R;
import com.svalero.tournaments.adapter.TeamsListAdapter;
import com.svalero.tournaments.contract.team.ListTeamsContract;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.presenter.TeamsListPresenter;

import java.util.ArrayList;
import java.util.List;

public class TeamsListView extends AppCompatActivity implements ListTeamsContract.View {

    private Team selectedTeam;
    private TeamsListAdapter adapter;
    private List<Team> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_list_view);

        ListTeamsContract.Presenter presenter = new TeamsListPresenter(this);
        presenter.loadTeams();

        teamList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.listTeamsRecycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TeamsListAdapter(teamList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void listTeams(List<Team> teamsList) {
        this.teamList.clear();
        this.teamList.addAll(teamsList);
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

    public void setSelectedTeam(Team team){
        this.selectedTeam = team;
    }
}