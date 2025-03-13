package com.svalero.tournaments.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.tournaments.domain.UserData;

@Dao
public interface UserDataDao {

    @Query("SELECT * FROM user_data WHERE username = :username")
    UserData getUserData(String username);

    @Insert
    void insert(UserData data);

    @Update
    void update(UserData data);

    @Query("DELETE FROM user_data WHERE username = :name")
    void deleteByName(String name);
}
