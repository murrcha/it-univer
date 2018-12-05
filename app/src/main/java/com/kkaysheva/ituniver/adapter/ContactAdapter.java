package com.kkaysheva.ituniver.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.model.Contact;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * ContactAdapter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> implements Filterable {

    private static final String TAG = ContactAdapter.class.getSimpleName();
    private OnItemClickListener listener;
    private final List<Contact> contacts = new ArrayList<>();
    private final List<Contact> contactsFull = new ArrayList<>();
    private Queue<List<Contact>> pendingUpdates = new ArrayDeque<>();

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredContacts = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredContacts.addAll(contactsFull);
            } else {
                String filter = constraint.toString().toLowerCase().trim();
                for (Contact contact : contactsFull) {
                    if (contact.getName().toLowerCase().contains(filter)) {
                        filteredContacts.add(contact);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredContacts;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Contact> filteredContacts = new ArrayList<>((List<Contact>) results.values);
            updateItems(filteredContacts, true);
        }
    };

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

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    public void updateItems(final List<Contact> newContacts, boolean isSearch) {
        pendingUpdates.add(newContacts);
        if (pendingUpdates.size() > 1) {
            return;
        }
        updateItemsInternal(newContacts, isSearch);
    }

    private void updateItemsInternal(final List<Contact> newContacts, boolean isSearch) {
        final List<Contact> oldContacts = new ArrayList<>(this.contacts);
        final Handler handler = new Handler();
        new Thread(() -> {
            final DiffUtil.DiffResult diffResult =
                    DiffUtil.calculateDiff(new ContactDiffCallback(oldContacts, newContacts));
            handler.post(() -> applyDiffResult(newContacts, diffResult, isSearch));
        }).start();
    }

    private void applyDiffResult(List<Contact> newContacts, DiffUtil.DiffResult diffResult, boolean isSearch) {
        pendingUpdates.remove();
        dispatchUpdates(newContacts, diffResult, isSearch);
        if (pendingUpdates.size() > 0) {
            updateItemsInternal(pendingUpdates.peek(), isSearch);
        }
    }

    private void dispatchUpdates(List<Contact> newContacts, DiffUtil.DiffResult diffResult, boolean isSearch) {
        diffResult.dispatchUpdatesTo(this);
        contacts.clear();
        contacts.addAll(newContacts);
        if (!isSearch) {
            contactsFull.clear();
            contactsFull.addAll(newContacts);
        }
    }

    class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView name;
        private final TextView number;

        ContactHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            itemView.setOnClickListener(this);
        }

        void bind(Contact contact) {
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
