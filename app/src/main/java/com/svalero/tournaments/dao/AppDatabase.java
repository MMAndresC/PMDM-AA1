package com.svalero.tournaments.dao;

import androidx.room.RoomDatabase;

import com.svalero.tournaments.domain.UserData;
import com.svalero.tournaments.domain.UserFavourite;

@androidx.room.Database(entities= {UserData.class, UserFavourite.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDataDao userDataDao();
    public abstract UserFavouriteDao userFavouriteDao();

}
