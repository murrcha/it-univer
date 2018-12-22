package com.kkaysheva.ituniver.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kkaysheva.ituniver.model.ContactInfo;

/**
 * AppDatabase
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Database(entities = {ContactInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao getContactDaO();
}
