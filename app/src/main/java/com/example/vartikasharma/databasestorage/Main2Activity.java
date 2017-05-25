package com.example.vartikasharma.databasestorage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {
    private static String LOG_TAG = Main2Activity.class.getSimpleName();
    List<ContactCard> contactCardList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getNameEmailDetails();

    }

    private void getNameEmailDetails() {
        Context context = getApplicationContext();
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[]{ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID};
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur != null && cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                Log.i(LOG_TAG, "name, " + name);
                String photo = cur.getString(2);
                Log.i(LOG_TAG, "photo, " + photo);
                String phoneNumber = cur.getString(3).replaceAll("\\s+","");
                Log.i(LOG_TAG, "phoneNumber, " + phoneNumber);
                String lastTimeContacted = cur.getString(4);
                Log.i(LOG_TAG, "lastTimeContacted, " + lastTimeContacted);
                String contactId = cur.getString(4);
                Log.i(LOG_TAG, "contact id, " + contactId);
                String email = getEmailAddress(contactId);
                Log.i(LOG_TAG, "email, " + email);
                Log.i(LOG_TAG, "name, "+ name +  "photo, " + photo + "email, "+ email);
                Uri image = null;
                if (photo != null){
                    image = Uri.parse(photo);
                    Log.i(LOG_TAG, "image," + image);
                }
                if (!lastTimeContacted.equals("0") && !nameList.contains(name)) {
                     Date callDateTime = new Date(Long.valueOf(lastTimeContacted));
                     Log.i(LOG_TAG, "callDayTime, " + callDateTime);
                     nameList.add(name);
                     ContactCard contactCard = new ContactCard(name, phoneNumber, callDateTime, email, image);
                     contactCardList.add(contactCard);
                }
            } while (cur.moveToNext());
            Log.i(LOG_TAG, "size, "+ contactCardList.size());
        }

        cur.close();
        ContactAdapter contactAdapter = new ContactAdapter(contactCardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
    }

    private String getEmailAddress(String contactId) {
        String emailAddress = "";
        final String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Email.TYPE
        };
        final Cursor email = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, projection,
                ContactsContract.Data.CONTACT_ID + "=?", new String[]{String.valueOf(contactId)}, null);

        if (email != null && email.moveToFirst()) {
            final int contactEmailColumnIndex = email.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

                emailAddress = emailAddress + email.getString(contactEmailColumnIndex);
                Log.i(LOG_TAG, "emailAddress, " + emailAddress);
                email.moveToNext();
        }
        email.close();
        return emailAddress;
    }
}
