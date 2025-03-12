package com.svalero.tournaments.contract.team;

import com.svalero.tournaments.contract.tournament.RemoveTournamentContract;

public interface RemoveTeamContract {

    interface Model {
        interface OnRemoveTeamListener {
            void onRemoveTeamSuccess(String message);
            void onRemoveTeamError(String message);
        }
        void removeTeam(String token, long id, OnRemoveTeamListener listener);
    }

    interface View {
        void deletedTeam(String message);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void removeTeam(String token, long id);
    }
}
