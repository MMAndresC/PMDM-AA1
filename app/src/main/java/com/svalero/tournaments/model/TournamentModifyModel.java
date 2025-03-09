package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.TournamentApiInterface;
import com.svalero.tournaments.contract.TournamentModifyContract;
import com.svalero.tournaments.domain.Tournament;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentModifyModel implements TournamentModifyContract.Model {

    @Override
    public void modifyTournament(Tournament tournament, long id, OnModifyTournamentListener listener) {
        TournamentApiInterface api = TournamentApi.getTournamentApi();
        Call<Tournament> getTournamentCall = api.modifyTournament(tournament, id);
        getTournamentCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onModifyTournamentSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onModifyTournamentError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onModifyTournamentError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onModifyTournamentError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                listener.onModifyTournamentError("Request failed: " + t.getMessage());
            }
        });
    }
}
