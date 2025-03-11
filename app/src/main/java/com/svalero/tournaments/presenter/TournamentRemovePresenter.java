package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.tournament.RemoveTournamentContract;
import com.svalero.tournaments.model.TournamentRemoveModel;

public class TournamentRemovePresenter implements RemoveTournamentContract.Presenter, RemoveTournamentContract.Model.OnRemoveTournamentListener {

    private RemoveTournamentContract.View view;
    private RemoveTournamentContract.Model model;

    public TournamentRemovePresenter(RemoveTournamentContract.View view){
        this.view = view;
        this.model = new TournamentRemoveModel();
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
