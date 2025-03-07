package com.svalero.tournaments.contract;

import com.svalero.tournaments.domain.Tournament;


public interface TournamentAddContract {
    interface Model {
        interface OnSaveTournamentListener {
            void onSaveTournamentSuccess(Tournament tournament);
            void onSaveTournamentError(String message);
        }
        void saveTournament(Tournament tournament, OnSaveTournamentListener listener);
    }

    interface View {
      /*  void addTournament(Tournament tournament);*/
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void saveTournament(Tournament tournament);
    }
}
