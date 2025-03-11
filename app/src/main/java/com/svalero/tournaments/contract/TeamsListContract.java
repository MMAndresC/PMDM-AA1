package com.svalero.tournaments.contract;

import com.svalero.tournaments.domain.Team;

import java.util.List;

public interface TeamsListContract {
    interface Model {
        interface OnLoadTeamsListener {
            void onLoadTeamsSuccess(List<Team> teamsList);
            void onLoadTeamsError(String message);
        }
        void loadTeams(OnLoadTeamsListener listener);
    }

    interface View {
        void listTeams(List<Team> teamsList);
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void loadTeams();
    }
}
