package com.kkaysheva.ituniver.domain.mapper;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.kkaysheva.ituniver.data.network.directions.DirectionsResponse;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * DirectionsResponseToListLatLngMapperTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public class DirectionsResponseToListLatLngMapperTest {

    private DirectionsResponse response;

    @Before
    public void setup() throws FileNotFoundException {
        ClassLoader loader = getClass().getClassLoader();
        File file = new File(loader.getResource("directions.json").getFile());
        Reader reader = new FileReader(file);
        Gson gson = new Gson();
        response = gson.fromJson(reader, DirectionsResponse.class);
    }

    @Test
    public void whenCallMapForDirectionsResponseThenReturnListLatLng() {
        String POLYLINE = "g~_zIurvcIjA[@FB@B@dAQPdKBzBFdBT|JB~Af@AZ@NJVZTNFALC";
        List<LatLng> latLngList = PolyUtil.decode(POLYLINE);
        DirectionsResponseToListLatLngMapper mapper = new DirectionsResponseToListLatLngMapper();
        assertThat(mapper.map(response), is(latLngList));
    }
}