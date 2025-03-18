package com.svalero.tournaments.contract.tournament;

import com.svalero.tournaments.domain.Tournament;

public interface ModifyTournamentContract {

    interface Model {
        interface OnModifyTournamentListener {
            void onModifyTournamentSuccess(Tournament tournament);
            void onModifyTournamentError(String message);
        }
        void modifyTournament(Tournament tournament, long id, ModifyTournamentContract.Model.OnModifyTournamentListener listener);
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
