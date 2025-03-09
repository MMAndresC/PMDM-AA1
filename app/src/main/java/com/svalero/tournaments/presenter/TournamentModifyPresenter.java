package com.svalero.tournaments.presenter;

import com.svalero.tournaments.contract.TournamentModifyContract;
import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.model.TournamentModifyModel;

public class TournamentModifyPresenter implements TournamentModifyContract.Presenter, TournamentModifyContract.Model.OnModifyTournamentListener {
    private TournamentModifyContract.Model model;
    private TournamentModifyContract.View view;

    public TournamentModifyPresenter(TournamentModifyContract.View view){
        this.view = view;
        this.model = new TournamentModifyModel();
    }
    @Override
    public void onModifyTournamentSuccess(Tournament tournament) {
        view.updatedTournament(tournament);
    }

    @Override
    public void onModifyTournamentError(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void modifyTournament(Tournament tournament, long id) {
        model.modifyTournament(tournament, id, this);
    }
}
