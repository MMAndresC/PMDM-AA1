package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.TournamentApiInterface;
import com.svalero.tournaments.contract.TournamentAddContract;
import com.svalero.tournaments.domain.Tournament;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentAddModel implements TournamentAddContract.Model {
    @Override
    public void saveTournament(Tournament tournament, OnSaveTournamentListener listener) {
        TournamentApiInterface api = TournamentApi.getTournamentApi();
        Call<Tournament> getTournamentCall = api.addTournament(tournament);
        getTournamentCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onSaveTournamentSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onSaveTournamentError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onSaveTournamentError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onSaveTournamentError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                listener.onSaveTournamentError("Request failed: " + t.getMessage());
            }
        });
    }

}
