package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.TeamsListContract;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.model.TeamsListModel;

import java.util.List;

public class TeamsListPresenter implements TeamsListContract.Presenter, TeamsListContract.Model.OnLoadTeamsListener {

    private TeamsListContract.View view;
    private TeamsListContract.Model model;

    public TeamsListPresenter(TeamsListContract.View view){
        this.view = view;
        this.model = new TeamsListModel();
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
