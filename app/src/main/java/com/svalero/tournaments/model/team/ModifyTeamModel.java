package com.svalero.tournaments.model.team;

import com.svalero.tournaments.api.TeamApiInterface;
import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.contract.team.ModifyTeamContract;
import com.svalero.tournaments.domain.Team;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyTeamModel implements ModifyTeamContract.Model {
    @Override
    public void modifyTeam(Team team, long id, OnModifyTeamListener listener) {
        TeamApiInterface api = TournamentApi.getTeamApi();
        Call<Team> getTeamCall = api.modifyTeam(team, id);
        getTeamCall.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onModifyTeamSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onModifyTeamError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onModifyTeamError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onModifyTeamError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                listener.onModifyTeamError("Request failed: " + t.getMessage());
            }
        });
    }
}
