package com.svalero.tournaments.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "user_favourite", primaryKeys = {"username", "teamId"})
public class UserFavourite {
    private long teamId;
   @NonNull
   private String username;


    public UserFavourite(@NonNull String username, long teamId){
        this.teamId = teamId;
        this.username = username;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }
}
