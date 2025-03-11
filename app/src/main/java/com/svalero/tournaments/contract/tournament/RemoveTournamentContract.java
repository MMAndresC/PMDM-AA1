package com.svalero.tournaments.contract.tournament;


public interface RemoveTournamentContract {
    interface Model {
        interface OnRemoveTournamentListener {
            void onRemoveTournamentSuccess(String message);
            void onRemoveTournamentError(String message);
        }
        void removeTournament(String token, long id, OnRemoveTournamentListener listener);
    }

    interface View {
        void deletedTournament(String message);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void removeTournament(String token, long id);
    }
}
