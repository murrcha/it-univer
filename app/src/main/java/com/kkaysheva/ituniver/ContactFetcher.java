package com.kkaysheva.ituniver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ContactFetcher
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class ContactFetcher {

    public static List<Contact> getContacts(@NonNull Context context) {
        List<Contact> contacts = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                CommonDataKinds.Phone.CONTENT_URI,
                new String[]{Contacts._ID,
                        Contacts.DISPLAY_NAME,
                        CommonDataKinds.Phone.NUMBER},
                null,
                null,
                null
        );
        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                    contacts.add(new Contact(id, name, number));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contacts;
    }

    public static Contact getContactById(int contactId, @NonNull Context context) {
        Contact contact = new Contact();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                CommonDataKinds.Phone.CONTENT_URI,
                new String[]{Contacts.DISPLAY_NAME,
                        Contacts.PHOTO_URI,
                        CommonDataKinds.Phone.NUMBER},
                String.format("%s = ?", Contacts._ID),
                new String[]{String.valueOf(contactId)},
                null
        );
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                String photoUri = cursor.getString(cursor.getColumnIndex(Contacts.PHOTO_URI));
                String number = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                contact.setId(contactId);
                contact.setName(name);
                contact.setPhotoUri(photoUri);
                contact.setNumber(number);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contact;
    }
}