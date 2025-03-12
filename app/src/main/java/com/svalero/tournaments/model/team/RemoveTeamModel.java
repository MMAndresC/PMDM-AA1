package com.svalero.tournaments.model.team;

import com.svalero.tournaments.api.TeamApiInterface;
import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.contract.team.RemoveTeamContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveTeamModel implements RemoveTeamContract.Model {
    @Override
    public void removeTeam(String token, long id, OnRemoveTeamListener listener) {
        TeamApiInterface api = TournamentApi.getTeamApi();
        Call<Void> getTeamCall = api.deleteTeam(token, id);
        getTeamCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onRemoveTeamSuccess("Tournament with id " + id + " deleted");
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onRemoveTeamError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onRemoveTeamError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onRemoveTeamError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onRemoveTeamError("Request failed: " + t.getMessage());
            }
        });
    }
}
