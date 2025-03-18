package com.svalero.tournaments.presenter.tournament;

import com.svalero.tournaments.contract.tournament.ListNextTournamentsContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.tournament.ListNextTournamentsModel;

import java.util.List;

public class ListNextTournamentsPresenter implements ListNextTournamentsContract.Presenter, ListNextTournamentsContract.Model.OnLoadNextTournamentsListener {

    private ListNextTournamentsContract.View view;
    private ListNextTournamentsContract.Model model;

    public ListNextTournamentsPresenter(ListNextTournamentsContract.View view){
        this.view = view;
        this.model = new ListNextTournamentsModel();
    }
    @Override
    public void onLoadNextTournamentsSuccess(List<Tournament> tournamentsList) {
        view.listNextTournaments(tournamentsList);
    }

    @Override
    public void onLoadNextTournamentsError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void loadNextTournaments() {
        model.loadNextTournaments(this);
    }
}
