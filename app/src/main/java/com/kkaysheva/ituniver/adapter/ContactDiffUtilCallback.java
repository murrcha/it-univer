package com.kkaysheva.ituniver.adapter;

import android.support.v7.util.DiffUtil;

import com.kkaysheva.ituniver.model.Contact;

import java.util.List;

/**
 * ContactDiffUtilCallback
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactDiffUtilCallback extends DiffUtil.Callback {

    private List<Contact> current;
    private List<Contact> next;

    public ContactDiffUtilCallback(List<Contact> current, List<Contact> next) {
        this.current = current;
        this.next = next;
    }

    @Override
    public int getOldListSize() {
        return current.size();
    }

    @Override
    public int getNewListSize() {
        return next.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Contact oldContact = current.get(oldItemPosition);
        Contact newContact = next.get(newItemPosition);
        return oldContact.getId() == newContact.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Contact oldContact = current.get(oldItemPosition);
        Contact newContact = next.get(newItemPosition);
        return oldContact.getName().equals(newContact.getName())
                && oldContact.getNumber().equals(newContact.getNumber());
    }
}
