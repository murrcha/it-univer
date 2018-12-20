package com.kkaysheva.ituniver.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.model.Contact;

import java.util.List;

/**
 * ContactAdapter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private static final String TAG = ContactAdapter.class.getSimpleName();
    private OnItemClickListener listener;
    private final AsyncListDiffer<Contact> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item_view, viewGroup, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder contactHolder, int position) {
        contactHolder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitItems(List<Contact> contacts) {
        differ.submitList(contacts);
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
                Contact contact = differ.getCurrentList().get(position);
                listener.onItemClicked(contact.getId());
            }
        }
    }

    public static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldContact, @NonNull Contact newContact) {
            return oldContact.getId() == newContact.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldContact, @NonNull Contact newContact) {
            return oldContact.getName().equals(newContact.getName())
                    && oldContact.getNumber().equals(newContact.getNumber());
        }
    };

    public interface OnItemClickListener {
        void onItemClicked(int contactId);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
