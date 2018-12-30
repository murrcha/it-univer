package com.kkaysheva.ituniver.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * GeoCodeResponse
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public class GeoCodeResponse {

    @SerializedName("GeoObjectCollection")
    private final GeoObjectCollection geoObjectCollection;

    public GeoCodeResponse(GeoObjectCollection geoObjectCollection) {
        this.geoObjectCollection = geoObjectCollection;
    }

    public GeoObjectCollection getGeoObjectCollection() {
        return geoObjectCollection;
    }

    public String getAddress() {
        return "test";
    }

    public static class GeoObjectCollection {

        @SerializedName("featureMember")
        private final List<GeoObject> geoObjects;

        public GeoObjectCollection(List<GeoObject> geoObjects) {
            this.geoObjects = geoObjects;
        }

        public List<GeoObject> getGeoObjects() {
            return geoObjects;
        }
    }
}
