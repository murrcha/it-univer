package com.kkaysheva.ituniver.domain.model;

/**
 * Contact
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public final class Contact {

    private final int id;
    private final String name;
    private final String number;
    private final String photoUri;

    public Contact(int id, String name, String number, String photoUri) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.photoUri = photoUri;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPhotoUri() {
        return photoUri;
    }
}
