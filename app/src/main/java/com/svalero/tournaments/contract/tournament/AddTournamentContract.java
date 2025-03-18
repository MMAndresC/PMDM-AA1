package com.svalero.tournaments.contract.tournament;

import com.svalero.tournaments.domain.Tournament;


public interface AddTournamentContract {
    interface Model {
        interface OnSaveTournamentListener {
            void onSaveTournamentSuccess(Tournament tournament);
            void onSaveTournamentError(String message);
        }
        void saveTournament(Tournament tournament, OnSaveTournamentListener listener);
    }

    interface View {
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void saveTournament(Tournament tournament);
    }
}
