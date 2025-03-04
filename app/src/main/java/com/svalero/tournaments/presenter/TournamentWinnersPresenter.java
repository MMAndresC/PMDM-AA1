package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.TournamentWinnersContract;
import com.svalero.tournaments.domain.TournamentWinners;
import com.svalero.tournaments.model.TournamentWinnersModel;

import java.util.List;

public class TournamentWinnersPresenter implements TournamentWinnersContract.Presenter, TournamentWinnersContract.Model.OnLoadTournamentWinnersListener {
    private TournamentWinnersContract.View view;
    private TournamentWinnersContract.Model model;

    public TournamentWinnersPresenter(TournamentWinnersContract.View view, long tournamentId){
        this.view = view;
        this.model = new TournamentWinnersModel(tournamentId);
    }
    @Override
    public void loadTournamentWinners() {
        model.loadTournamentWinners(this);
    }


    @Override
    public void onLoadTournamentWinnersSuccess(List<TournamentWinners> winnersList) {
        view.listTournamentWinners(winnersList);
    }

    @Override
    public void onLoadTournamentWinnersError(String message) {
        view.showErrorMessage(message);
    }
}
