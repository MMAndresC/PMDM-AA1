package com.svalero.tournaments.presenter.tournament;

import com.svalero.tournaments.contract.tournament.AddTournamentContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.tournament.AddTournamentModel;

public class AddTournamentPresenter implements AddTournamentContract.Presenter, AddTournamentContract.Model.OnSaveTournamentListener {

    private AddTournamentContract.View view;
    private AddTournamentContract.Model model;

    public AddTournamentPresenter(AddTournamentContract.View view){
        this.view = view;
        this.model = new AddTournamentModel();
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
