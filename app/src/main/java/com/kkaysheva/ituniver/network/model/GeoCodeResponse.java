package com.kkaysheva.ituniver.network.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * GeoCodeResponse
 *
 * @since generated 12.2018
 */
public class GeoCodeResponse {

    /**
     * response : {"geoObjectCollection":{...}}
     */

    @SerializedName("response")
    private ResponseBean response;

    public static GeoCodeResponse objectFromData(String str) {

        return new Gson().fromJson(str, GeoCodeResponse.class);
    }

    public static List<GeoCodeResponse> arrayResponseModelFromData(String str) {

        Type listType = new TypeToken<ArrayList<GeoCodeResponse>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * geoObjectCollection : {"metaDataProperty":{},"featureMember":[{"geoObject":...}]}
         */

        @SerializedName("GeoObjectCollection")
        private GeoObjectCollectionBean geoObjectCollection;

        public static ResponseBean objectFromData(String str) {

            return new Gson().fromJson(str, ResponseBean.class);
        }

        public static List<ResponseBean> arrayResponseBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResponseBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public GeoObjectCollectionBean getGeoObjectCollection() {
            return geoObjectCollection;
        }

        public void setGeoObjectCollection(GeoObjectCollectionBean geoObjectCollection) {
            this.geoObjectCollection = geoObjectCollection;
        }

        public static class GeoObjectCollectionBean {
            /**
             * metaDataProperty : {}
             * featureMember : ...
             */

            @SerializedName("metaDataProperty")
            private MetaDataPropertyBean metaDataProperty;
            @SerializedName("featureMember")
            private List<FeatureMemberBean> featureMember;

            public static GeoObjectCollectionBean objectFromData(String str) {

                return new Gson().fromJson(str, GeoObjectCollectionBean.class);
            }

            public static List<GeoObjectCollectionBean> arrayGeoObjectCollectionBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<GeoObjectCollectionBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public MetaDataPropertyBean getMetaDataProperty() {
                return metaDataProperty;
            }

            public void setMetaDataProperty(MetaDataPropertyBean metaDataProperty) {
                this.metaDataProperty = metaDataProperty;
            }

            public List<FeatureMemberBean> getFeatureMember() {
                return featureMember;
            }

            public void setFeatureMember(List<FeatureMemberBean> featureMember) {
                this.featureMember = featureMember;
            }

            public static class MetaDataPropertyBean {
                public static MetaDataPropertyBean objectFromData(String str) {

                    return new Gson().fromJson(str, MetaDataPropertyBean.class);
                }

                public static List<MetaDataPropertyBean> arrayMetaDataPropertyBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<MetaDataPropertyBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }
            }

            public static class FeatureMemberBean {
                /**
                 * geoObject : {"metaDataProperty":{"geocoderMetaData":...}}
                 */

                @SerializedName("GeoObject")
                private GeoObjectBean geoObject;

                public static FeatureMemberBean objectFromData(String str) {

                    return new Gson().fromJson(str, FeatureMemberBean.class);
                }

                public static List<FeatureMemberBean> arrayFeatureMemberBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<FeatureMemberBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public GeoObjectBean getGeoObject() {
                    return geoObject;
                }

                public void setGeoObject(GeoObjectBean geoObject) {
                    this.geoObject = geoObject;
                }

                public static class GeoObjectBean {
                    /**
                     * metaDataProperty : {"geocoderMetaData": ...
                     */

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

                    public static GeoObjectBean objectFromData(String str) {

                        return new Gson().fromJson(str, GeoObjectBean.class);
                    }

                    public static List<GeoObjectBean> arrayGeoObjectBeanFromData(String str) {

                        Type listType = new TypeToken<ArrayList<GeoObjectBean>>() {
                        }.getType();

                        return new Gson().fromJson(str, listType);
                    }

                    public MetaDataPropertyBeanX getMetaDataProperty() {
                        return metaDataProperty;
                    }

                    public void setMetaDataProperty(MetaDataPropertyBeanX metaDataProperty) {
                        this.metaDataProperty = metaDataProperty;
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

                    public void setBoundedBy(BoundedByBean boundedBy) {
                        this.boundedBy = boundedBy;
                    }

                    public PointBean getPoint() {
                        return point;
                    }

                    public void setPoint(PointBean point) {
                        this.point = point;
                    }

                    public static class MetaDataPropertyBeanX {
                        /**
                         * geocoderMetaData : {"kind":...}
                         */

                        @SerializedName("GeocoderMetaData")
                        private GeocoderMetaDataBean geocoderMetaData;

                        public static MetaDataPropertyBeanX objectFromData(String str) {

                            return new Gson().fromJson(str, MetaDataPropertyBeanX.class);
                        }

                        public static List<MetaDataPropertyBeanX> arrayMetaDataPropertyBeanXFromData(String str) {

                            Type listType = new TypeToken<ArrayList<MetaDataPropertyBeanX>>() {
                            }.getType();

                            return new Gson().fromJson(str, listType);
                        }

                        public GeocoderMetaDataBean getGeocoderMetaData() {
                            return geocoderMetaData;
                        }

                        public void setGeocoderMetaData(GeocoderMetaDataBean geocoderMetaData) {
                            this.geocoderMetaData = geocoderMetaData;
                        }

                        public static class GeocoderMetaDataBean {
                            /**
                             * kind : district
                             * text : Россия, Удмуртская Республика, Ижевск, Центральный жилой район
                             * precision : other
                             * address : {}
                             * addressDetails : {"country":{
                             * "addressLine":"Удмуртская Республика, Ижевск, Центральный жилой район",
                             * "countryNameCode":"RU","countryName":"Россия","administrativeArea":
                             * {"administrativeAreaName":"Удмуртская Республика","subAdministrativeArea":
                             * {"subAdministrativeAreaName":"городской округ Ижевск","locality":
                             * {"localityName":"Ижевск","dependentLocality":
                             * {"dependentLocalityName":"Центральный жилой район"}}}}}}
                             */

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

                            public static GeocoderMetaDataBean objectFromData(String str) {

                                return new Gson().fromJson(str, GeocoderMetaDataBean.class);
                            }

                            public static List<GeocoderMetaDataBean> arrayGeocoderMetaDataBeanFromData(String str) {

                                Type listType = new TypeToken<ArrayList<GeocoderMetaDataBean>>() {
                                }.getType();

                                return new Gson().fromJson(str, listType);
                            }

                            public String getKind() {
                                return kind;
                            }

                            public void setKind(String kind) {
                                this.kind = kind;
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

                            public void setPrecision(String precision) {
                                this.precision = precision;
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

                            public void setAddressDetails(AddressDetailsBean addressDetails) {
                                this.addressDetails = addressDetails;
                            }

                            public static class AddressBean {
                                public static AddressBean objectFromData(String str) {

                                    return new Gson().fromJson(str, AddressBean.class);
                                }

                                public static List<AddressBean> arrayAddressBeanFromData(String str) {

                                    Type listType = new TypeToken<ArrayList<AddressBean>>() {
                                    }.getType();

                                    return new Gson().fromJson(str, listType);
                                }
                            }

                            public static class AddressDetailsBean {
                                /**
                                 * country : {...}
                                 */

                                @SerializedName("Country")
                                private CountryBean country;

                                public static AddressDetailsBean objectFromData(String str) {

                                    return new Gson().fromJson(str, AddressDetailsBean.class);
                                }

                                public static List<AddressDetailsBean> arrayAddressDetailsBeanFromData(String str) {

                                    Type listType = new TypeToken<ArrayList<AddressDetailsBean>>() {
                                    }.getType();

                                    return new Gson().fromJson(str, listType);
                                }

                                public CountryBean getCountry() {
                                    return country;
                                }

                                public void setCountry(CountryBean country) {
                                    this.country = country;
                                }

                                public static class CountryBean {
                                    /**
                                     * addressLine : Удмуртская Республика, Ижевск, Центральный жилой район
                                     * countryNameCode : RU
                                     * countryName : Россия
                                     * administrativeArea : {"administrativeAreaName":"Удмуртская Республика",
                                     * "subAdministrativeArea":
                                     * {"subAdministrativeAreaName":"городской округ Ижевск","locality":
                                     * {"localityName":"Ижевск","dependentLocality":
                                     * {"dependentLocalityName":"Центральный жилой район"}}}}
                                     */

                                    @SerializedName("AddressLine")
                                    private String addressLine;
                                    @SerializedName("CountryNameCode")
                                    private String countryNameCode;
                                    @SerializedName("CountryName")
                                    private String countryName;
                                    @SerializedName("AdministrativeArea")
                                    private AdministrativeAreaBean administrativeArea;

                                    public static CountryBean objectFromData(String str) {

                                        return new Gson().fromJson(str, CountryBean.class);
                                    }

                                    public static List<CountryBean> arrayCountryBeanFromData(String str) {

                                        Type listType = new TypeToken<ArrayList<CountryBean>>() {
                                        }.getType();

                                        return new Gson().fromJson(str, listType);
                                    }

                                    public String getAddressLine() {
                                        return addressLine;
                                    }

                                    public void setAddressLine(String addressLine) {
                                        this.addressLine = addressLine;
                                    }

                                    public String getCountryNameCode() {
                                        return countryNameCode;
                                    }

                                    public void setCountryNameCode(String countryNameCode) {
                                        this.countryNameCode = countryNameCode;
                                    }

                                    public String getCountryName() {
                                        return countryName;
                                    }

                                    public void setCountryName(String countryName) {
                                        this.countryName = countryName;
                                    }

                                    public AdministrativeAreaBean getAdministrativeArea() {
                                        return administrativeArea;
                                    }

                                    public void setAdministrativeArea(AdministrativeAreaBean administrativeArea) {
                                        this.administrativeArea = administrativeArea;
                                    }

                                    public static class AdministrativeAreaBean {
                                        /**
                                         * administrativeAreaName : Удмуртская Республика
                                         * subAdministrativeArea :
                                         * {"subAdministrativeAreaName":"городской округ Ижевск",
                                         * "locality":{"localityName":"Ижевск","dependentLocality":
                                         * {"dependentLocalityName":"Центральный жилой район"}}}
                                         */

                                        @SerializedName("AdministrativeAreaName")
                                        private String administrativeAreaName;
                                        @SerializedName("SubAdministrativeArea")
                                        private SubAdministrativeAreaBean subAdministrativeArea;

                                        public static AdministrativeAreaBean objectFromData(String str) {

                                            return new Gson().fromJson(str, AdministrativeAreaBean.class);
                                        }

                                        public static List<AdministrativeAreaBean> arrayAdministrativeAreaBeanFromData(
                                                String str) {

                                            Type listType = new TypeToken<ArrayList<AdministrativeAreaBean>>() {
                                            }.getType();

                                            return new Gson().fromJson(str, listType);
                                        }

                                        public String getAdministrativeAreaName() {
                                            return administrativeAreaName;
                                        }

                                        public void setAdministrativeAreaName(String administrativeAreaName) {
                                            this.administrativeAreaName = administrativeAreaName;
                                        }

                                        public SubAdministrativeAreaBean getSubAdministrativeArea() {
                                            return subAdministrativeArea;
                                        }

                                        public void setSubAdministrativeArea(
                                                SubAdministrativeAreaBean subAdministrativeArea) {
                                            this.subAdministrativeArea = subAdministrativeArea;
                                        }

                                        public static class SubAdministrativeAreaBean {
                                            /**
                                             * subAdministrativeAreaName : городской округ Ижевск
                                             * locality : {"localityName":"Ижевск","dependentLocality":
                                             * {"dependentLocalityName":"Центральный жилой район"}}
                                             */

                                            @SerializedName("SubAdministrativeAreaName")
                                            private String subAdministrativeAreaName;
                                            @SerializedName("Locality")
                                            private LocalityBean locality;

                                            public static SubAdministrativeAreaBean objectFromData(String str) {

                                                return new Gson().fromJson(str, SubAdministrativeAreaBean.class);
                                            }

                                            public static List<SubAdministrativeAreaBean>
                                            arraySubAdministrativeAreaBeanFromData(String str) {

                                                Type listType = new TypeToken<ArrayList<SubAdministrativeAreaBean>>() {
                                                }.getType();

                                                return new Gson().fromJson(str, listType);
                                            }

                                            public String getSubAdministrativeAreaName() {
                                                return subAdministrativeAreaName;
                                            }

                                            public void setSubAdministrativeAreaName(String subAdministrativeAreaName) {
                                                this.subAdministrativeAreaName = subAdministrativeAreaName;
                                            }

                                            public LocalityBean getLocality() {
                                                return locality;
                                            }

                                            public void setLocality(LocalityBean locality) {
                                                this.locality = locality;
                                            }

                                            public static class LocalityBean {
                                                /**
                                                 * localityName : Ижевск
                                                 * dependentLocality :
                                                 * {"dependentLocalityName":"Центральный жилой район"}
                                                 */

                                                @SerializedName("LocalityName")
                                                private String localityName;
                                                @SerializedName("DependentLocality")
                                                private DependentLocalityBean dependentLocality;

                                                public static LocalityBean objectFromData(String str) {

                                                    return new Gson().fromJson(str, LocalityBean.class);
                                                }

                                                public static List<LocalityBean> arrayLocalityBeanFromData(String str) {

                                                    Type listType = new TypeToken<ArrayList<LocalityBean>>() {
                                                    }.getType();

                                                    return new Gson().fromJson(str, listType);
                                                }

                                                public String getLocalityName() {
                                                    return localityName;
                                                }

                                                public void setLocalityName(String localityName) {
                                                    this.localityName = localityName;
                                                }

                                                public DependentLocalityBean getDependentLocality() {
                                                    return dependentLocality;
                                                }

                                                public void setDependentLocality(
                                                        DependentLocalityBean dependentLocality) {
                                                    this.dependentLocality = dependentLocality;
                                                }

                                                public static class DependentLocalityBean {
                                                    /**
                                                     * dependentLocalityName : Центральный жилой район
                                                     */

                                                    @SerializedName("DependentLocalityName")
                                                    private String dependentLocalityName;

                                                    public static DependentLocalityBean objectFromData(String str) {

                                                        return new Gson().fromJson(str, DependentLocalityBean.class);
                                                    }

                                                    public static List<DependentLocalityBean>
                                                    arrayDependentLocalityBeanFromData(String str) {

                                                        Type listType =
                                                                new TypeToken<ArrayList<DependentLocalityBean>>() {
                                                        }.getType();

                                                        return new Gson().fromJson(str, listType);
                                                    }

                                                    public String getDependentLocalityName() {
                                                        return dependentLocalityName;
                                                    }

                                                    public void setDependentLocalityName(String dependentLocalityName) {
                                                        this.dependentLocalityName = dependentLocalityName;
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
                        public static BoundedByBean objectFromData(String str) {

                            return new Gson().fromJson(str, BoundedByBean.class);
                        }

                        public static List<BoundedByBean> arrayBoundedByBeanFromData(String str) {

                            Type listType = new TypeToken<ArrayList<BoundedByBean>>() {
                            }.getType();

                            return new Gson().fromJson(str, listType);
                        }
                    }

                    public static class PointBean {
                        public static PointBean objectFromData(String str) {

                            return new Gson().fromJson(str, PointBean.class);
                        }

                        public static List<PointBean> arrayPointBeanFromData(String str) {

                            Type listType = new TypeToken<ArrayList<PointBean>>() {
                            }.getType();

                            return new Gson().fromJson(str, listType);
                        }
                    }
                }
            }
        }
    }
}
