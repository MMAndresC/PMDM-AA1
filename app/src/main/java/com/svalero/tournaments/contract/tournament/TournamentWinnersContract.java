package com.svalero.tournaments.contract.tournament;

import com.svalero.tournaments.domain.TournamentWinners;

import java.util.List;

public interface TournamentWinnersContract {
    interface Model {
        interface OnLoadTournamentWinnersListener {
            void onLoadTournamentWinnersSuccess(List<TournamentWinners> winnersList);
            void onLoadTournamentWinnersError(String message);
        }
        void loadTournamentWinners(TournamentWinnersContract.Model.OnLoadTournamentWinnersListener listener);
    }

    interface View {
        void listTournamentWinners(List<TournamentWinners> winnersList);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void loadTournamentWinners();
    }
}
