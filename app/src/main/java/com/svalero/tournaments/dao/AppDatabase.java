package com.svalero.tournaments.dao;

import androidx.room.RoomDatabase;

import com.svalero.tournaments.domain.UserData;

@androidx.room.Database(entities= {UserData.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDataDao userDataDao();

}
