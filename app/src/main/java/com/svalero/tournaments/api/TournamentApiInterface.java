package com.svalero.tournaments.api;

import com.svalero.tournaments.domain.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TournamentApiInterface {

    @GET("tournaments")
    Call<List<Tournament>> getTournaments();

    @GET("tournaments/{id}")
    Call<Tournament> getTournament(@Path("id") int id);

    @GET("tournaments")
    Call<List<Tournament>> getNextTournamentsByDate(@Query("initDate") String initDate);
}
