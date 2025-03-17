package com.svalero.tournaments.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.svalero.tournaments.domain.UserFavourite;

import java.util.List;

@Dao
public interface UserFavouriteDao {

    @Query("SELECT * FROM user_favourite WHERE username = :username")
    List<UserFavourite> getFavouritesTeams(String username);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserFavourite userFavourite);

    @Query("DELETE FROM user_favourite WHERE username = :username AND teamId = :id")
    void deleteFavouriteTeam(String username, long id);
}
