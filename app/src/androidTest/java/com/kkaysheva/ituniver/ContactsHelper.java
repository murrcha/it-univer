package com.kkaysheva.ituniver;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class ContactsHelper {

    private static final String FAKE_NUMBER = "0987654321011";

    private ContactsHelper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void fill(@NonNull Context context) throws OperationApplicationException, RemoteException {
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();
        batch.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        batch.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "Alex")
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "Fetcher")
                .build());
        batch.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, FAKE_NUMBER)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());
        batch.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, "alex_fetcher@mail.com")
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build());
        context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, batch);
    }

    public static void clear(@NonNull Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        try (Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.CommonDataKinds.Phone.NUMBER},
                String.format(" %s = ? ", ContactsContract.CommonDataKinds.Phone.NUMBER),
                new String[]{FAKE_NUMBER},
                String.format("%s ASC LIMIT 1", ContactsContract.CommonDataKinds.Phone.NUMBER)
        )) {
            if (cursor == null) {
                return;
            }
            while (cursor.moveToNext()) {
                String key = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, key);
                contentResolver.delete(uri, null, null);
            }
        }
    }
}
