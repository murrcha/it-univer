package com.kkaysheva.ituniver.data.network.geocode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * GeoCodeResponse
 *
 * @since generated 12.2018
 */
public final class GeoCodeResponse {

    @SerializedName("response")
    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public static class ResponseBean {

        @SerializedName("GeoObjectCollection")
        private GeoObjectCollectionBean geoObjectCollection;

        public GeoObjectCollectionBean getGeoObjectCollection() {
            return geoObjectCollection;
        }

        public static class GeoObjectCollectionBean {

            @SerializedName("metaDataProperty")
            private MetaDataPropertyBean metaDataProperty;

            @SerializedName("featureMember")
            private List<FeatureMemberBean> featureMember;

            public MetaDataPropertyBean getMetaDataProperty() {
                return metaDataProperty;
            }

            public List<FeatureMemberBean> getFeatureMember() {
                return featureMember;
            }

            public static class MetaDataPropertyBean {

            }

            public static class FeatureMemberBean {

                @SerializedName("GeoObject")
                private GeoObjectBean geoObject;

                public GeoObjectBean getGeoObject() {
                    return geoObject;
                }

                public static class GeoObjectBean {

                    @SerializedName("metaDataProperty")
                    private MetaDataPropertyBeanX metaDataProperty;

                    @SerializedName("description")
                    private String description;

                    @SerializedName("name")
                    private String name;

                    @SerializedName("boundedBy")
                    private BoundedByBean boundedBy;

                    @SerializedName("Point")
                    private PointBean point;

                    public MetaDataPropertyBeanX getMetaDataProperty() {
                        return metaDataProperty;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public BoundedByBean getBoundedBy() {
                        return boundedBy;
                    }

                    public PointBean getPoint() {
                        return point;
                    }

                    public void setPoint(PointBean point) {
                        this.point = point;
                    }

                    public static class MetaDataPropertyBeanX {

                        @SerializedName("GeocoderMetaData")
                        private GeocoderMetaDataBean geocoderMetaData;

                        public GeocoderMetaDataBean getGeocoderMetaData() {
                            return geocoderMetaData;
                        }

                        public static class GeocoderMetaDataBean {

                            @SerializedName("kind")
                            private String kind;

                            @SerializedName("text")
                            private String text;

                            @SerializedName("precision")
                            private String precision;

                            @SerializedName("Address")
                            private AddressBean address;

                            @SerializedName("AddressDetails")
                            private AddressDetailsBean addressDetails;

                            public String getKind() {
                                return kind;
                            }

                            public String getText() {
                                return text;
                            }

                            public void setText(String text) {
                                this.text = text;
                            }

                            public String getPrecision() {
                                return precision;
                            }

                            public AddressBean getAddress() {
                                return address;
                            }

                            public void setAddress(AddressBean address) {
                                this.address = address;
                            }

                            public AddressDetailsBean getAddressDetails() {
                                return addressDetails;
                            }

                            public static class AddressBean {

                            }

                            public static class AddressDetailsBean {

                                @SerializedName("Country")
                                private CountryBean country;

                                public CountryBean getCountry() {
                                    return country;
                                }

                                public static class CountryBean {

                                    @SerializedName("AddressLine")
                                    private String addressLine;

                                    @SerializedName("CountryNameCode")
                                    private String countryNameCode;

                                    @SerializedName("CountryName")
                                    private String countryName;

                                    @SerializedName("AdministrativeArea")
                                    private AdministrativeAreaBean administrativeArea;

                                    public String getAddressLine() {
                                        return addressLine;
                                    }

                                    public String getCountryNameCode() {
                                        return countryNameCode;
                                    }

                                    public String getCountryName() {
                                        return countryName;
                                    }

                                    public AdministrativeAreaBean getAdministrativeArea() {
                                        return administrativeArea;
                                    }

                                    public static class AdministrativeAreaBean {

                                        @SerializedName("AdministrativeAreaName")
                                        private String administrativeAreaName;

                                        @SerializedName("SubAdministrativeArea")
                                        private SubAdministrativeAreaBean subAdministrativeArea;

                                        public String getAdministrativeAreaName() {
                                            return administrativeAreaName;
                                        }

                                        public SubAdministrativeAreaBean getSubAdministrativeArea() {
                                            return subAdministrativeArea;
                                        }

                                        public static class SubAdministrativeAreaBean {

                                            @SerializedName("SubAdministrativeAreaName")
                                            private String subAdministrativeAreaName;

                                            @SerializedName("Locality")
                                            private LocalityBean locality;

                                            public String getSubAdministrativeAreaName() {
                                                return subAdministrativeAreaName;
                                            }

                                            public LocalityBean getLocality() {
                                                return locality;
                                            }

                                            public static class LocalityBean {

                                                @SerializedName("LocalityName")
                                                private String localityName;

                                                @SerializedName("DependentLocality")
                                                private DependentLocalityBean dependentLocality;

                                                public String getLocalityName() {
                                                    return localityName;
                                                }

                                                public DependentLocalityBean getDependentLocality() {
                                                    return dependentLocality;
                                                }

                                                public static class DependentLocalityBean {

                                                    @SerializedName("DependentLocalityName")
                                                    private String dependentLocalityName;

                                                    public String getDependentLocalityName() {
                                                        return dependentLocalityName;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    public static class BoundedByBean {

                    }

                    public static class PointBean {

                    }
                }
            }
        }
    }
}
