package com.svalero.tournaments.contract.team;

import com.svalero.tournaments.domain.Team;

public interface ModifyTeamContract {

    interface Model {
        interface OnModifyTeamListener {
            void onModifyTeamSuccess(Team team);
            void onModifyTeamError(String message);
        }
        void modifyTeam(Team team, long id, ModifyTeamContract.Model.OnModifyTeamListener listener);
    }

    interface View {
        void updatedTeam(Team team);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void modifyTeam(Team team, long id);
    }
}
