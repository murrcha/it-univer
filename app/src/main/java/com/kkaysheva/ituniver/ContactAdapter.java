package com.kkaysheva.ituniver;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ContactAdapter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private static final String TAG = ContactAdapter.class.getSimpleName();

    private OnItemClickListener listener;

    private List<Contact> contacts = new ArrayList<>();

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item_view, viewGroup, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder contactHolder, int position) {
        contactHolder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setItems(List<Contact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
        notifyDataSetChanged();
    }

    class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView number;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            itemView.setOnClickListener(this);
        }

        public void bind(Contact contact) {
            name.setText(contact.getName());
            Log.d(TAG, "bind: " + contact.getName());
            number.setText(contact.getNumber());
            Log.d(TAG, "bind: " + contact.getNumber());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION) {
                Contact contact = contacts.get(position);
                listener.onItemClicked(contact.getId());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int contactId);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
