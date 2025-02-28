package com.svalero.tournaments.api;

import com.svalero.tournaments.domain.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TournamentApiInterface {
    //TODO domain team, player

    @GET("tournaments")
    Call<List<Tournament>>  getTournaments();
}
