package com.svalero.tournaments.presenter;


import com.svalero.tournaments.contract.TournamentsListContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.MainModel;

import java.util.List;

public class TournamentsListPresenter implements TournamentsListContract.Presenter, TournamentsListContract.Model.OnLoadTournamentsListener {

    private TournamentsListContract.View view;
    private TournamentsListContract.Model model;

    public TournamentsListPresenter(TournamentsListContract.View view){
        this.view = view;
        this.model = new MainModel();
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
