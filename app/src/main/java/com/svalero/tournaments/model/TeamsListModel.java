package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TeamApiInterface;
import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.contract.TeamsListContract;
import com.svalero.tournaments.domain.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsListModel implements TeamsListContract.Model {

    @Override
    public void loadTeams(OnLoadTeamsListener listener) {
        TeamApiInterface api = TournamentApi.getTeamApi();
        Call<List<Team>> getTeamCall = api.getTeams();
        getTeamCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onLoadTeamsSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onLoadTeamsError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onLoadTeamsError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onLoadTeamsError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                listener.onLoadTeamsError("Request failed: " + t.getMessage());
            }
        });
    }
}
