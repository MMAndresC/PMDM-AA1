package com.svalero.tournaments.contract.tournament;

import com.svalero.tournaments.domain.Tournament;

import java.util.List;

public interface ListNextTournamentsContract {

    interface Model {
        interface OnLoadNextTournamentsListener {
            void onLoadNextTournamentsSuccess(List<Tournament> tournamentsList);
            void onLoadNextTournamentsError(String message);
        }
        void loadNextTournaments(ListNextTournamentsContract.Model.OnLoadNextTournamentsListener listener);
    }

    interface View {
        void listNextTournaments(List<Tournament> tournamentsList);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void loadNextTournaments();
    }
}
