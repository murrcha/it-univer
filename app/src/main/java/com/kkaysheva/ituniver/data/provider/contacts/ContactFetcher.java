package com.kkaysheva.ituniver.data.provider.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.model.Contact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * ContactFetcher
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class ContactFetcher {

    private ContactFetcher() {
    }

    public static Single<List<Contact>> getContacts(@NonNull Context context) {
        return Single.fromCallable(() -> {
            List<Contact> contacts = new ArrayList<>();
            ContentResolver contentResolver = context.getContentResolver();
            try (Cursor cursor = contentResolver.query(
                    CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{Contacts._ID,
                            Contacts.DISPLAY_NAME,
                            CommonDataKinds.Phone.NUMBER},
                    null,
                    null,
                    String.format("%s ASC", Contacts.DISPLAY_NAME)
            )) {
                contacts.addAll(getContactsFromCursor(cursor));
            }
            return contacts;
        });
    }

    public static Single<List<Contact>> getContactsByName(@NonNull String searchName, @NonNull Context context) {
        return Single.fromCallable(() -> {
            List<Contact> contacts = new ArrayList<>();
            ContentResolver contentResolver = context.getContentResolver();
            try (Cursor cursor = contentResolver.query(
                    CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{Contacts._ID,
                            Contacts.DISPLAY_NAME,
                            CommonDataKinds.Phone.NUMBER},
                    String.format(" %s LIKE ? ", Contacts.DISPLAY_NAME),
                    new String[]{"%" + searchName + "%"},
                    String.format("%s ASC", Contacts.DISPLAY_NAME)
            )) {
                contacts.addAll(getContactsFromCursor(cursor));
            }
            return contacts;
        });
    }

    public static Single<Contact> getContactById(int contactId, @NonNull Context context) {
        return Single.fromCallable(() -> {
            Contact contact = null;
            ContentResolver contentResolver = context.getContentResolver();
            try (Cursor cursor = contentResolver.query(
                    CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{Contacts.DISPLAY_NAME,
                            Contacts.PHOTO_URI,
                            CommonDataKinds.Phone.NUMBER},
                    String.format("%s = ?", Contacts._ID),
                    new String[]{String.valueOf(contactId)},
                    null
            )) {
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                    String photoUri = cursor.getString(cursor.getColumnIndex(Contacts.PHOTO_URI));
                    String number = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                    contact = new Contact(contactId, name, number, photoUri);
                }
            }
            return contact;
        });
    }

    private static List<Contact> getContactsFromCursor(Cursor cursor) {
        List<Contact> contacts = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                contacts.add(new Contact(id, name, number, null));
            }
        }
        return contacts;
    }
}
