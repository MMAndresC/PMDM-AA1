package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.TournamentApiInterface;
import com.svalero.tournaments.contract.TournamentWinnersContract;
import com.svalero.tournaments.domain.TournamentWinners;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentWinnersModel implements TournamentWinnersContract.Model {

    private long tournamentId;

    public TournamentWinnersModel(long tournamentId){
        this.tournamentId = tournamentId;
    }
    @Override
    public void loadTournamentWinners(OnLoadTournamentWinnersListener listener) {
        TournamentApiInterface api = TournamentApi.getTournamentApi();
        Call<List<TournamentWinners>> getTournamentWinnersCall = api.getTournamentWinners(tournamentId);
        getTournamentWinnersCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<TournamentWinners>> call, Response<List<TournamentWinners>> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onLoadTournamentWinnersSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onLoadTournamentWinnersError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onLoadTournamentWinnersError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onLoadTournamentWinnersError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<TournamentWinners>> call, Throwable t) {
                listener.onLoadTournamentWinnersError("Request failed: " + t.getMessage());
            }
        });
    }
}
