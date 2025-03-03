package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.TournamentApiInterface;
import com.svalero.tournaments.contract.TournamentsListContract;
import com.svalero.tournaments.domain.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel implements TournamentsListContract.Model {
    @Override
    public void loadTournaments(OnLoadTournamentsListener listener) {
        TournamentApiInterface tournamentApiInterface = TournamentApi.buildInstance();
        String today = String.valueOf(java.time.LocalDate.now());
        Call<List<Tournament>> getTournamentsCall= tournamentApiInterface.getNextTournamentsByDate(today);
        getTournamentsCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onLoadTournamentsSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                        listener.onLoadTournamentsError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onLoadTournamentsError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onLoadTournamentsError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                listener.onLoadTournamentsError("Request failed: " + t.getMessage());
            }
        });
    }
}
