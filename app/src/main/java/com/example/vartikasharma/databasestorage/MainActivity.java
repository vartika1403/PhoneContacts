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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = MainActivity.class.getSimpleName();
    List<ContactCard> contactCardList = new ArrayList<>();
    ArrayList<String> personNameList = new ArrayList<>();
    ArrayList<String> imageUri = new ArrayList<>();
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
        String filter = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, filter, null, null);
        if (cur != null && cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String photo = cur.getString(2);
                String phoneNumber = cur.getString(3).replaceAll("\\s+", "");
                String lastTimeContacted = cur.getString(4);
                String contactId = cur.getString(4);
                String email = getEmailAddress(contactId);
                Uri image = null;
                if (photo != null && !imageUri.contains(photo)) {
                    image = Uri.parse(photo);
                    imageUri.add(photo);
                }
                if (!lastTimeContacted.equals("0") && !personNameList.contains(name)) {
                    Date callDateTime = new Date(Long.valueOf(lastTimeContacted));
                    personNameList.add(name);
                    ContactCard contactCard = new ContactCard(name, phoneNumber, callDateTime, email, image);
                    contactCardList.add(contactCard);
                }
            } while (cur.moveToNext());
            cur.close();
        }

        Collections.sort(contactCardList, new Comparator<ContactCard>() {
            @Override
            public int compare(ContactCard contactCard1, ContactCard contactCard2) {
                return contactCard1.getLastCallDayTime().after(contactCard2.getLastCallDayTime()) ? -1
                        : contactCard1.getLastCallDayTime().equals(contactCard2.getLastCallDayTime()) ? 0 : 1;
            }
        });
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

        if (email != null) {
            while (email.moveToFirst()) {
                final int contactEmailColumnIndex = email.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                emailAddress = emailAddress + email.getString(contactEmailColumnIndex);
            }
            email.close();
        }
        return emailAddress;
    }
}
