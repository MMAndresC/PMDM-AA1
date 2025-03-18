package com.svalero.tournaments.api;

import com.svalero.tournaments.domain.Tournament;
import com.svalero.tournaments.domain.TournamentWinners;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TournamentApiInterface {

    @GET("tournaments")
    Call<List<Tournament>> getTournaments();

    @GET("tournaments/{id}")
    Call<Tournament> getTournament(@Path("id") long id);

    @GET("tournaments")
    Call<List<Tournament>> getNextTournamentsByDate(@Query("initDate") String initDate);

    @GET("tournaments/{id}/match-winners")
    Call<List<TournamentWinners>> getTournamentWinners(@Path("id") long id);

    @POST("tournaments")
    Call<Tournament> addTournament(@Body Tournament tournament);

    @DELETE("tournaments/{id}")
    Call<Void> deleteTournament(@Header("Authorization") String token, @Path("id") long id);

    @PUT("tournaments/{id}")
    Call<Tournament> modifyTournament(@Body Tournament tournament, @Path("id") long id);
}
