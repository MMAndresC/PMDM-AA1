package com.svalero.tournaments.presenter.team;

import com.svalero.tournaments.contract.team.RemoveTeamContract;
import com.svalero.tournaments.model.team.RemoveTeamModel;

public class RemoveTeamPresenter implements RemoveTeamContract.Presenter, RemoveTeamContract.Model.OnRemoveTeamListener {

    private RemoveTeamContract.View view;
    private RemoveTeamContract.Model model;

    public RemoveTeamPresenter(RemoveTeamContract.View view){
        this.view = view;
        this.model = new RemoveTeamModel();
    }
    @Override
    public void onRemoveTeamSuccess(String message) {
        view.deletedTeam(message);
    }

    @Override
    public void onRemoveTeamError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void removeTeam(String token, long id) {
        model.removeTeam(token, id, this);
    }
}
