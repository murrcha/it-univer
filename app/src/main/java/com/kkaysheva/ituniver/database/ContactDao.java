package com.kkaysheva.ituniver.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kkaysheva.ituniver.model.ContactInfo;

import java.util.List;

import io.reactivex.Single;

/**
 * ContactDao
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Dao
public interface ContactDao {

    @NonNull
    @Query("SELECT * FROM contacts")
    Single<List<ContactInfo>> getAll();

    @Nullable
    @Query("SELECT * FROM contacts WHERE id = :id")
    Single<ContactInfo> getContactInfoById(Long id);

    @Update
    void updateContactInfo(ContactInfo contactInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactInfo(ContactInfo contactInfo);

    @Delete
    void deleteContactInfo(ContactInfo contactInfo);

    @Query("DELETE FROM contacts")
    void deleteAll();
}
