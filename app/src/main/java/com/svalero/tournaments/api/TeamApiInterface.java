package com.svalero.tournaments.api;

import com.svalero.tournaments.domain.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TeamApiInterface {

    @GET("teams")
    Call<List<Team>> getTeams();
}
