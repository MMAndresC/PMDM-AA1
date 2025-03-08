package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.TournamentApiInterface;
import com.svalero.tournaments.contract.TournamentRemoveContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentRemoveModel implements TournamentRemoveContract.Model {

    @Override
    public void removeTournament(String token, long id, OnRemoveTournamentListener listener) {
        TournamentApiInterface api = TournamentApi.getTournamentApi();
        Call<Void> getTournamentCall = api.deleteTournament(token, id);
        getTournamentCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onRemoveTournamentSuccess("Tournament with id " + id + " deleted");
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onRemoveTournamentError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onRemoveTournamentError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onRemoveTournamentError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onRemoveTournamentError("Request failed: " + t.getMessage());
            }
        });
    }
}
