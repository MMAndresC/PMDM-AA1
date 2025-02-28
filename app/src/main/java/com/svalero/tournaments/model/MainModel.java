package com.svalero.tournaments.model;

import com.svalero.tournaments.api.TournamentApi;
import com.svalero.tournaments.api.TournamentApiInterface;
import com.svalero.tournaments.contract.MainContract;
import com.svalero.tournaments.domain.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel implements MainContract.Model {
    @Override
    public void loadTournaments(OnLoadTournamentsListener listener) {
        TournamentApiInterface tournamentApiInterface = TournamentApi.buildInstance();
        Call<List<Tournament>> getTournamentsCall= tournamentApiInterface.getTournaments();
        getTournamentsCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                if (response.code() == 200) {
                    listener.onLoadTournamentsSuccess(response.body());
                } else if (response.code() == 500) {
                    listener.onLoadTournamentsError("The API is not available.  Please try again");
                } else {
                    listener.onLoadTournamentsError(String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                listener.onLoadTournamentsError("Could not connect to the data source.  Please check the connection and try again.");
            }
        });
    }
}
