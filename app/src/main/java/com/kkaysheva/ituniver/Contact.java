package com.kkaysheva.ituniver;

import java.io.Serializable;

/**
 * Contact
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class Contact implements Serializable {

    private int id;
    private String name;
    private String number;
    private String photoUri;

    public Contact() {

    }

    public Contact(int id, String name, String number) {
        this.name = name;
        this.number = number;
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
