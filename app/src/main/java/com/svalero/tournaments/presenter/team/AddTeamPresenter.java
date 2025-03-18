package com.svalero.tournaments.presenter.team;

import com.svalero.tournaments.contract.team.AddTeamContract;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.model.team.AddTeamModel;

public class AddTeamPresenter implements AddTeamContract.Presenter, AddTeamContract.Model.OnSaveTeamListener {
    private AddTeamContract.View view;
    private AddTeamContract.Model model;

    public AddTeamPresenter(AddTeamContract.View view){
        this.view = view;
        this.model = new AddTeamModel();
    }
    @Override
    public void onSaveTeamSuccess(Team team) {
        view.showSuccessMessage("New tournament added with id " + team.getId());
    }

    @Override
    public void onSaveTeamError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void saveTeam(Team team) {
        model.saveTeam(team, this);
    }
}
