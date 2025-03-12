package com.svalero.tournaments.presenter.tournament;

import com.svalero.tournaments.contract.tournament.RemoveTournamentContract;
import com.svalero.tournaments.model.tournament.RemoveTournamentModel;

public class RemoveTournamentPresenter implements RemoveTournamentContract.Presenter, RemoveTournamentContract.Model.OnRemoveTournamentListener {

    private RemoveTournamentContract.View view;
    private RemoveTournamentContract.Model model;

    public RemoveTournamentPresenter(RemoveTournamentContract.View view){
        this.view = view;
        this.model = new RemoveTournamentModel();
    }
    @Override
    public void removeTournament(String token, long id) {
        model.removeTournament(token, id, this);
    }

    @Override
    public void onRemoveTournamentSuccess(String message) {
        view.deletedTournament(message);
    }

    @Override
    public void onRemoveTournamentError(String message) {
        view.showErrorMessage(message);
    }
}
