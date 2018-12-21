package com.kkaysheva.ituniver.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kkaysheva.ituniver.model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao getContactDaO();
}
