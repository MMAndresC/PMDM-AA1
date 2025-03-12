package com.svalero.tournaments.presenter.team;

import com.svalero.tournaments.contract.team.ModifyTeamContract;
import com.svalero.tournaments.domain.Team;
import com.svalero.tournaments.model.team.ModifyTeamModel;

public class ModifyTeamPresenter implements ModifyTeamContract.Presenter, ModifyTeamContract.Model.OnModifyTeamListener {

    private ModifyTeamContract.View view;
    private ModifyTeamContract.Model model;

    public ModifyTeamPresenter(ModifyTeamContract.View view){
        this.view = view;
        this.model = new ModifyTeamModel();
    }
    @Override
    public void onModifyTeamSuccess(Team team) {
        view.updatedTeam(team);
    }

    @Override
    public void onModifyTeamError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void modifyTeam(Team team, long id) {
        model.modifyTeam(team, id, this);
    }
}
