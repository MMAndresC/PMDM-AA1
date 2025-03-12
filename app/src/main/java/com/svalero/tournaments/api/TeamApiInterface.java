package com.svalero.tournaments.api;

import com.svalero.tournaments.domain.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeamApiInterface {

    @GET("teams")
    Call<List<Team>> getTeams();

    @POST("teams")
    Call<Team> addTeam(@Body Team team);

    @DELETE("teams/{id}")
    Call<Void> deleteTeam(@Header("Authorization") String token, @Path("id") long id);

    @PUT("teams/{id}")
    Call<Team> modifyTeam(@Body Team team, @Path("id") long id);

}
