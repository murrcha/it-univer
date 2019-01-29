package com.kkaysheva.ituniver.domain.mapper;

import com.google.gson.Gson;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeResponse;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GeoCodeResponseToStringMapperTest
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class GeoCodeResponseToStringMapperTest {

    private GeoCodeResponse response;

    @Before
    public void before() throws FileNotFoundException {
        ClassLoader loader = getClass().getClassLoader();
        File file = new File(loader.getResource("geocode.json").getFile());
        Reader reader = new FileReader(file);
        Gson gson = new Gson();
        response = gson.fromJson(reader, GeoCodeResponse.class);
    }

    @Test
    public void whenCallMapGeoCodeResponseThenReturnAddressString() {
        GeoCodeResponseToStringMapper mapper = new GeoCodeResponseToStringMapper();
        String ADDRESS = "Удмуртская Республика, Ижевск, Пушкинская улица, 253Б";
        assertThat(mapper.map(response)).isEqualTo(ADDRESS);
    }
}