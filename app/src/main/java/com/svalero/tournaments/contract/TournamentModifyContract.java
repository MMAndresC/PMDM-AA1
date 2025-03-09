package com.svalero.tournaments.contract;

import com.svalero.tournaments.domain.Tournament;

public interface TournamentModifyContract {

    interface Model {
        interface OnModifyTournamentListener {
            void onModifyTournamentSuccess(Tournament tournament);
            void onModifyTournamentError(String message);
        }
        void modifyTournament(Tournament tournament, long id, TournamentModifyContract.Model.OnModifyTournamentListener listener);
    }

    interface View {
        void updatedTournament(Tournament tournament);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void modifyTournament(Tournament tournament, long id);
    }
}
