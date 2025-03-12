package com.svalero.tournaments.presenter.tournament;

import com.svalero.tournaments.contract.tournament.ModifyTournamentContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.tournament.ModifyTournamentModel;

public class ModifyTournamentPresenter implements ModifyTournamentContract.Presenter, ModifyTournamentContract.Model.OnModifyTournamentListener {
    private ModifyTournamentContract.Model model;
    private ModifyTournamentContract.View view;

    public ModifyTournamentPresenter(ModifyTournamentContract.View view){
        this.view = view;
        this.model = new ModifyTournamentModel();
    }
    @Override
    public void onModifyTournamentSuccess(Tournament tournament) {
        view.updatedTournament(tournament);
    }

    @Override
    public void onModifyTournamentError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void modifyTournament(Tournament tournament, long id) {
        model.modifyTournament(tournament, id, this);
    }
}
