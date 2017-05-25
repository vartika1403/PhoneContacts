package com.example.vartikasharma.databasestorage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<ContactCard> contactsList;

    public ContactAdapter(List<ContactCard> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_card_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactCard contactCard = contactsList.get(position);
        holder.contactName.invalidate();
        holder.contactName.setText("");
        holder.mobileNo.invalidate();
        holder.mobileNo.setText("");
        holder.lastCallDayTime.invalidate();
        holder.lastCallDayTime.setText("");
        holder.email.invalidate();
        holder.email.setText("");
        holder.contactName.setText("Name : " + contactCard.getContactName());
        holder.mobileNo.setText("Mobile no : " + contactCard.getMobileNo());
        holder.lastCallDayTime.setText("Last Call Time : " + contactCard.getLastCallDayTime());
        if (!contactCard.getEmailAddresss().equals("")) {
            holder.email.setText("Email Address : " + contactCard.getEmailAddresss());
        } else {
            holder.email.setText("Email Address : " + "not available");
        }
        holder.contactImage.setImageURI(contactCard.getContactImage());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName, mobileNo, lastCallDayTime, email;
        public  ImageView contactImage;

        public MyViewHolder(View view) {
            super(view);
            contactName = (TextView) view.findViewById(R.id.contact_name);
            mobileNo = (TextView) view.findViewById(R.id.mobile);
            lastCallDayTime = (TextView) view.findViewById(R.id.last_contact_time);
            email = (TextView) view.findViewById(R.id.email);
            contactImage = (ImageView) view.findViewById(R.id.image);
        }
    }
}
