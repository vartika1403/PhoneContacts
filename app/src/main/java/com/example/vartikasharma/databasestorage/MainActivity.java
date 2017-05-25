package com.example.vartikasharma.databasestorage;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String contactID;     // contacts unique ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i(LOG_TAG, "onCreated is called");
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);

        Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Log.i(LOG_TAG, "permission not granted");
            return;
        }

        List<ContactCard> contactCardList = new ArrayList<>();
        while ( cur != null && cur.getCount() > 0 && cur.moveToNext() ) {

            String contactId = cur.getString(cur
                            .getColumnIndex(ContactsContract.Contacts._ID));

            Cursor emailCur = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                    new String[]{contactId}, null);
            while (emailCur.moveToNext()) {

                String name = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String lastTimeContacted = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED));
                Log.i(LOG_TAG, "lastTimeContacted, " + lastTimeContacted);

                Log.i(LOG_TAG, "name, " + name);
                Log.i(LOG_TAG, "phoneNumber, " + phoneNumber);

                Uri u = getPhotoUri(phoneNumber);
                Date callDayTime = null;
                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                Log.i(LOG_TAG, "email address, " + email);

               /* if (!lastTimeContacted.equals("0")) {*/
                    callDayTime = new Date(Long.valueOf(lastTimeContacted));
                    Log.i(LOG_TAG, " callDayTime, " + callDayTime);
                    ContactCard contactCard = new ContactCard(name, phoneNumber, callDayTime, email, u);
                    contactCardList.add(contactCard);

                    Log.i(LOG_TAG, " callDayTime>>, " + callDayTime);
              //  }

            }
            emailCur.close();
        }

           cur.close();
            /*Collections.sort(contactCardList, new Comparator<ContactCard>() {
                @Override
                public int compare(ContactCard contactCard1, ContactCard contactCard2) {
                    return contactCard1.() < contactCard2.getTotalCallDuration() ? -1
                            : contactCard1.getTotalCallDuration() == contactCard2.getTotalCallDuration() ? 0 : 1;
                }
            });
            Collections.reverse(contactCardList);*/
            ContactAdapter contactAdapter = new ContactAdapter(contactCardList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(contactAdapter);
        }


    private String getDuartion() {
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";

        Calendar calender = Calendar.getInstance();


        calender.set(2016, calender.NOVEMBER, 18);
        String fromDate = String.valueOf(calender.getTimeInMillis());
        Log.i(LOG_TAG, "fromDate," + fromDate);

        calender.set(2017, calender.MARCH, 20);
        String toDate = String.valueOf(calender.getTimeInMillis());
        Log.i(LOG_TAG, "toDate," + toDate);

        String[] whereValue = {fromDate, toDate};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        // Log.i(LOG_TAG, "mangedCursor," + c);
        String duration = "";

        return duration;
    }


   /* private String getEmailAddress() {
   *//*     String emailAddress = "";
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));


            *//**//*    if (emailCur != null) {
               *//**//**//**//*     while (emailCur.moveToNext()) {
                        emailAddress = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.i(LOG_TAG, "email address," + emailAddress);

                        // emailList.add(email); // Here you will get list of email

                    }
                    emailCur.close();
                }*//**//**//**//*
            }
            cur.close();
        }
        return  emailAddress;*//**//*
    }*//*
*/


    private Uri getPhotoUri(String contactNumber) {

        Cursor contactLookupCursor = getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contactNumber), new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
      if (contactLookupCursor != null) {
          while (contactLookupCursor.moveToNext()) {
              contactID = contactLookupCursor.getString(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
              Log.i(LOG_TAG, "contact id," + contactID);
          }
          contactLookupCursor.close();
      }

        if (contactID != null) {
            Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
                    .parseLong(contactID));
            return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        }
        return  null;
    }
}
