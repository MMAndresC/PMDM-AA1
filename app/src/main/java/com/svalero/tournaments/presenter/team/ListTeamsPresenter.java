package com.svalero.tournaments.presenter.team;

import com.svalero.tournaments.contract.team.ListTeamsContract;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.model.team.ListTeamsModel;

import java.util.List;

public class ListTeamsPresenter implements ListTeamsContract.Presenter, ListTeamsContract.Model.OnLoadTeamsListener {

    private ListTeamsContract.View view;
    private ListTeamsContract.Model model;

    public ListTeamsPresenter(ListTeamsContract.View view){
        this.view = view;
        this.model = new ListTeamsModel();
    }
    @Override
    public void onLoadTeamsSuccess(List<Team> teamsList) {
        view.listTeams(teamsList);
    }

    @Override
    public void onLoadTeamsError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void loadTeams() {
        model.loadTeams(this);
    }
}
