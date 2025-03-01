package com.svalero.tournaments.presenter;


import com.svalero.tournaments.contract.MainContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.MainModel;

import java.util.List;

public class MainPresenter implements MainContract.Presenter, MainContract.Model.OnLoadTournamentsListener {

    private MainContract.View view;
    private MainContract.Model model;

    public MainPresenter(MainContract.View view){
        this.view = view;
        model = new MainModel();
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
