package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.MainContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.TournamentsListModel;

import java.util.List;

public class TournamentsListPresenter implements MainContract.Presenter, MainContract.Model.OnLoadTournamentsListener {

    private MainContract.Model model;
    private MainContract.View view;

    public TournamentsListPresenter(MainContract.View view){
        this.view = view;
        this.model = new TournamentsListModel();
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
