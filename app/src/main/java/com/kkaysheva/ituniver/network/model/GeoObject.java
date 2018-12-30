package com.kkaysheva.ituniver.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * GeoObject
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public class GeoObject {

    @SerializedName("metaDataProperty")
    private final MetaDataProperty metaDataProperty;

    public GeoObject(MetaDataProperty metaDataProperty) {
        this.metaDataProperty = metaDataProperty;
    }

    public MetaDataProperty getMetaDataProperty() {
        return metaDataProperty;
    }

    public static class MetaDataProperty {

        @SerializedName("GeocoderMetaData")
        private final GeocoderMetaData geocoderMetaData;

        public MetaDataProperty(GeocoderMetaData geocoderMetaData) {
            this.geocoderMetaData = geocoderMetaData;
        }

        public GeocoderMetaData getGeocoderMetaData() {
            return geocoderMetaData;
        }

        public static class GeocoderMetaData {

            @SerializedName("AddressDetails")
            private final AddressDetails addressDetails;

            public GeocoderMetaData(AddressDetails addressDetails) {
                this.addressDetails = addressDetails;
            }

            public AddressDetails getAddressDetails() {
                return addressDetails;
            }

            public static class AddressDetails {

                @SerializedName("Country")
                private final Country country;

                public AddressDetails(Country country) {
                    this.country = country;
                }

                public Country getCountry() {
                    return country;
                }

                public static class Country {

                    @SerializedName("AddressLine")
                    private final String addressLine;

                    public Country(String addressLine) {
                        this.addressLine = addressLine;
                    }

                    public String getAddressLine() {
                        return addressLine;
                    }
                }
            }
        }
    }
}
