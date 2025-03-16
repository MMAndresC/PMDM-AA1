package com.svalero.tournaments.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.tournaments.domain.User;
import com.svalero.tournaments.domain.UserData;

import java.util.List;

@Dao
public interface UserDataDao {

    @Query("SELECT * FROM user_data WHERE username = :username")
    UserData getUserData(String username);

    @Query("SELECT * FROM user_data")
    List<UserData> getUsers();

    @Insert
    void insert(UserData data);

    @Query("UPDATE user_data SET alias = :alias, image = :image, region = :region, mainRole = :mainRole WHERE username = :username")
    void update(String alias, byte[] image, int region, String mainRole, String username);

    @Query("DELETE FROM user_data WHERE username = :name")
    void deleteByName(String name);
}
