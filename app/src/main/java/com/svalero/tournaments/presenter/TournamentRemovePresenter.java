package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.TournamentRemoveContract;
import com.svalero.tournaments.model.TournamentRemoveModel;

public class TournamentRemovePresenter implements TournamentRemoveContract.Presenter, TournamentRemoveContract.Model.OnRemoveTournamentListener {

    private TournamentRemoveContract.View view;
    private TournamentRemoveContract.Model model;

    public TournamentRemovePresenter(TournamentRemoveContract.View view){
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
