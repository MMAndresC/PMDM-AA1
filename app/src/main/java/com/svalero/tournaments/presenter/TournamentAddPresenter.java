package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.TournamentAddContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.TournamentAddModel;

public class TournamentAddPresenter implements TournamentAddContract.Presenter, TournamentAddContract.Model.OnSaveTournamentListener {

    private TournamentAddContract.View view;
    private TournamentAddContract.Model model;

    public TournamentAddPresenter(TournamentAddContract.View view){
        this.view = view;
        this.model = new TournamentAddModel();
    }
    @Override
    public void saveTournament(Tournament tournament) {
        model.saveTournament(tournament, this);
    }

    @Override
    public void onSaveTournamentSuccess(Tournament tournament) {
        view.showSuccessMessage("New tournament added with id " + tournament.getId());
    }

    @Override
    public void onSaveTournamentError(String message) {
        view.showErrorMessage(message);
    }
}
