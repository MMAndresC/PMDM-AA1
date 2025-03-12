package com.svalero.tournaments.contract.team;

import com.svalero.tournaments.domain.Team;

public interface AddTeamContract {

    interface Model {
        interface OnSaveTeamListener {
            void onSaveTeamSuccess(Team team);
            void onSaveTeamError(String message);
        }
        void saveTeam(Team team, AddTeamContract.Model.OnSaveTeamListener listener);
    }

    interface View {
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void saveTeam(Team team);
    }
}
