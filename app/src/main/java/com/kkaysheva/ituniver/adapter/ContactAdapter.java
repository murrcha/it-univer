package com.kkaysheva.ituniver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.model.Contact;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ContactAdapter
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private static final String TAG = ContactAdapter.class.getSimpleName();
    private OnItemClickListener listener;
    private final List<Contact> contacts = new ArrayList<>();
    private final Queue<List<Contact>> pendingUpdates = new ArrayDeque<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

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

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void updateItems(final List<Contact> newContacts) {
        pendingUpdates.add(newContacts);
        if (pendingUpdates.size() > 1) {
            return;
        }
        updateItemsInternal(newContacts);
    }

    private void updateItemsInternal(final List<Contact> newContacts) {
        final List<Contact> oldContacts = new ArrayList<>(this.contacts);
        Observable.fromCallable(() ->
            DiffUtil.calculateDiff(new ContactDiffUtilCallback(oldContacts, newContacts)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(compositeDisposable::add)
                .doOnNext(diffResult -> applyDiffResult(newContacts, diffResult))
                .doOnComplete(() -> Log.d(TAG, "onComplete: done"))
                .doOnError(throwable -> Log.e(TAG, "onError: ", throwable))
                .subscribe();
    }

    private void applyDiffResult(List<Contact> newContacts, DiffUtil.DiffResult diffResult) {
        pendingUpdates.remove();
        dispatchUpdates(newContacts, diffResult);
        if (pendingUpdates.size() > 0) {
            updateItemsInternal(pendingUpdates.peek());
        }
    }

    private void dispatchUpdates(List<Contact> newContacts, DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(this);
        contacts.clear();
        contacts.addAll(newContacts);
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
