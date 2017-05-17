package com.example.vartikasharma.databasestorage;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.contactName.setText(contactCard.getContactName());
        holder.email.setText(contactCard.getEmail());
        holder.mobileNo.setText(contactCard.getMobileNo());
        holder.lastCallTime.setText(contactCard.getLastCallTime());
        holder.totalTalkTime.setText(contactCard.getTotalCallDuration());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName, mobileNo, email, lastCallTime, totalTalkTime;

        public MyViewHolder(View view) {
            super(view);
            contactName = (TextView) view.findViewById(R.id.contact_name);
            mobileNo = (TextView) view.findViewById(R.id.mobile);
            email = (TextView) view.findViewById(R.id.email);
            lastCallTime = (TextView) view.findViewById(R.id.last_call_time);
            totalTalkTime = (TextView) view.findViewById(R.id.total_call_duration);
        }
    }
}
