package com.svalero.tournaments.contract;

import com.svalero.tournaments.domain.Tournament;

import java.util.List;

public interface TournamentsListContract {

    interface Model {
        interface OnLoadTournamentsListener {
            void onLoadTournamentsSuccess(List<Tournament> tournamentsList);
            void onLoadTournamentsError(String message);
        }
        void loadTournaments(OnLoadTournamentsListener listener);
    }

    interface View {
        void listTournaments(List<Tournament> tournamentsList);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void loadTournaments();
    }
}
