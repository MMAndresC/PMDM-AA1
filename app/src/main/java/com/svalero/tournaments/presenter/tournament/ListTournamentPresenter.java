package com.svalero.tournaments.presenter.tournament;


import com.svalero.tournaments.contract.tournament.ListTournamentsContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.MainModel;
import com.svalero.tournaments.model.tournament.ListTournamentsModel;

import java.util.List;

public class ListTournamentPresenter implements ListTournamentsContract.Presenter, ListTournamentsContract.Model.OnLoadTournamentsListener {

    private ListTournamentsContract.View view;
    private ListTournamentsContract.Model model;

    public ListTournamentPresenter(ListTournamentsContract.View view){
        this.view = view;
        this.model = new ListTournamentsModel();
    }
    @Override
    public void loadTournaments() {
        model.loadTournaments(this);
    }

    @Override
    public void onLoadTournamentsSuccess(List<Tournament> tournamentsList) {
        view.listTournaments(tournamentsList);
    }

    @Override
    public void onLoadTournamentsError(String message) {
        view.showErrorMessage(message);
    }
}
