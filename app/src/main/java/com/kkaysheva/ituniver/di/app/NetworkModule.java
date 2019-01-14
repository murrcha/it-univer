package com.kkaysheva.ituniver.di.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kkaysheva.ituniver.BuildConfig;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeApi;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kkaysheva.ituniver.data.network.geocode.GeoCodeApi.GEO_BASE_URL;
import static com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsApi.GOOGLE_BASE_URL;

/**
 * NetworkModule
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
@Module
public final class NetworkModule {

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        return client.build();
    }

    @Provides
    @Singleton
    public GeoCodeApi provideGeoCodeApi(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(GEO_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(GeoCodeApi.class);
    }

    @Provides
    @Singleton
    public GoogleDirectionsApi provideGoogleDirectionsApi(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(GOOGLE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(GoogleDirectionsApi.class);
    }
}
