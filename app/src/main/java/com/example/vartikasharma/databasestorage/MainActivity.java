package com.example.vartikasharma.databasestorage;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<ContactCard> contactCardList;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Log.i(LOG_TAG, "permission not granted");
            return;
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        contactCardList = new ArrayList<>();

        while (phones != null && phones.moveToNext() && managedCursor != null && managedCursor.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String callDuration = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.DURATION));

            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.i(LOG_TAG, "name, " + name);
            Log.i(LOG_TAG, "phoneNumber, " + phoneNumber);
            if (Integer.parseInt(callDuration) > 0) {
                Log.i(LOG_TAG, "call duration, " + callDuration + name);
            }
            ContactCard contactCard = new ContactCard(name, phoneNumber,"vartika.1403@gmail.com", "11 AM",callDuration);
            contactCardList.add(contactCard);
        }
        phones.close();
        contactAdapter = new ContactAdapter(contactCardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
    }
}
