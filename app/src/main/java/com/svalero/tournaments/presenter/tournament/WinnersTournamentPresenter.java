package com.svalero.tournaments.presenter.tournament;

import com.svalero.tournaments.contract.tournament.TournamentWinnersContract;
import com.svalero.tournaments.domain.TournamentWinners;
import com.svalero.tournaments.model.tournament.WinnersTournamentModel;

import java.util.List;

public class WinnersTournamentPresenter implements TournamentWinnersContract.Presenter, TournamentWinnersContract.Model.OnLoadTournamentWinnersListener {
    private TournamentWinnersContract.View view;
    private TournamentWinnersContract.Model model;

    public WinnersTournamentPresenter(TournamentWinnersContract.View view, long tournamentId){
        this.view = view;
        this.model = new WinnersTournamentModel(tournamentId);
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
