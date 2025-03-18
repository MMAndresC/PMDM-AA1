package com.svalero.tournaments.model.tournament;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.TournamentApiInterface;
import com.svalero.tournaments.contract.tournament.ListNextTournamentsContract;
import com.svalero.tournaments.domain.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNextTournamentsModel implements ListNextTournamentsContract.Model {
    @Override
    public void loadNextTournaments(OnLoadNextTournamentsListener listener) {
        TournamentApiInterface tournamentApiInterface = TournamentApi.getTournamentApi();
        String today = String.valueOf(java.time.LocalDate.now());
        Call<List<Tournament>> getNextTournamentsCall= tournamentApiInterface.getNextTournamentsByDate(today);
        getNextTournamentsCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                int statusCode = response.code();
                if (statusCode >= 200 && statusCode < 300) {
                    listener.onLoadNextTournamentsSuccess(response.body());
                } else if (statusCode >= 400 && statusCode < 500) {
                    listener.onLoadNextTournamentsError("Client error: " + statusCode);
                } else if (statusCode >= 500 && statusCode < 600) {
                    listener.onLoadNextTournamentsError("Server error: " + statusCode + " The API is not available.  Please try again");
                } else {
                    listener.onLoadNextTournamentsError("Unexpected response: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                listener.onLoadNextTournamentsError("Request failed: " + t.getMessage());
            }
        });
    }
}
