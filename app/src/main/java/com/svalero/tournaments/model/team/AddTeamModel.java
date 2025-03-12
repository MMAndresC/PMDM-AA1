package com.svalero.tournaments.model.team;

import com.svalero.tournaments.api.TeamApiInterface;
import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.contract.team.AddTeamContract;
import com.svalero.tournaments.domain.Team;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeamModel implements AddTeamContract.Model {
    @Override
    public void saveTeam(Team team, OnSaveTeamListener listener) {
        TeamApiInterface api = TournamentApi.getTeamApi();
        Call<Team> getTeamCall = api.addTeam(team);
        getTeamCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onSaveTeamSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onSaveTeamError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onSaveTeamError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onSaveTeamError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                listener.onSaveTeamError("Request failed: " + t.getMessage());
            }
        });
    }
}
