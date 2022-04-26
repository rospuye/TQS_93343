package tqs.consume_external_api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc
public class C_IntegrationTests {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {

    }

    @Test
    void whenGetCountries_thenStatus200() throws Exception {
        mvc.perform(get("/countries").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(181))))
                .andExpect(jsonPath("$[0].all.country", is("Afghanistan")))
                .andExpect(jsonPath("$[180].all.country", is("Zimbabwe")));
    }

    @Test
    void whenGetCountryByName_thenStatus200() throws Exception {
        mvc.perform(get("/countries/name=France").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.all.country", is("France")))
                .andExpect(jsonPath("$.all.abbreviation", is("FR")))

                .andExpect(jsonPath("$.territories.[0].territory", is("French Guiana")))
                .andExpect(jsonPath("$.territories.[1].territory", is("French Polynesia")))
                .andExpect(jsonPath("$.territories.[2].territory", is("Guadeloupe")))
                .andExpect(jsonPath("$.territories.[3].territory", is("Martinique")))
                .andExpect(jsonPath("$.territories.[4].territory", is("Mayotte")))
                .andExpect(jsonPath("$.territories.[5].territory", is("New Caledonia")))
                .andExpect(jsonPath("$.territories.[6].territory", is("Reunion")))
                .andExpect(jsonPath("$.territories.[7].territory", is("Saint Barthelemy")))
                .andExpect(jsonPath("$.territories.[8].territory", is("Saint Pierre and Miquelon")))
                .andExpect(jsonPath("$.territories.[9].territory", is("St Martin")))
                .andExpect(jsonPath("$.territories.[10].territory", is("Wallis and Futuna")));
    }

    @Test
    void whenGetCountryByAb_thenStatus200() throws Exception {
        mvc.perform(get("/countries/ab=FR").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.all.country", is("France")))
                .andExpect(jsonPath("$.all.abbreviation", is("FR")))

                .andExpect(jsonPath("$.territories.[0].territory", is("French Guiana")))
                .andExpect(jsonPath("$.territories.[1].territory", is("French Polynesia")))
                .andExpect(jsonPath("$.territories.[2].territory", is("Guadeloupe")))
                .andExpect(jsonPath("$.territories.[3].territory", is("Martinique")))
                .andExpect(jsonPath("$.territories.[4].territory", is("Mayotte")))
                .andExpect(jsonPath("$.territories.[5].territory", is("New Caledonia")))
                .andExpect(jsonPath("$.territories.[6].territory", is("Reunion")))
                .andExpect(jsonPath("$.territories.[7].territory", is("Saint Barthelemy")))
                .andExpect(jsonPath("$.territories.[8].territory", is("Saint Pierre and Miquelon")))
                .andExpect(jsonPath("$.territories.[9].territory", is("St Martin")))
                .andExpect(jsonPath("$.territories.[10].territory", is("Wallis and Futuna")));
    }

    @Test
    void whenGetCountriesStatus_thenStatus200() throws Exception {
        mvc.perform(get("/countries/status=confirmed").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(181))))
                .andExpect(jsonPath("$[0].all.country", is("Afghanistan")))
                .andExpect(jsonPath("$[180].all.country", is("Zimbabwe")))
                .andExpect(jsonPath("$[0].all.confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$[0].all.deaths_dates", nullValue()));
    }

    @Test
    void whenGetCountryStatusByName_thenStatus200() throws Exception {
        mvc.perform(get("/countries/name=France/status=confirmed").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.all.country", is("France")))
                .andExpect(jsonPath("$.all.abbreviation", is("FR")))
                .andExpect(jsonPath("$.all.confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.all.deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[0].territory", is("French Guiana")))
                .andExpect(jsonPath("$.territories.[0].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[0].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[1].territory", is("French Polynesia")))
                .andExpect(jsonPath("$.territories.[1].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[1].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[2].territory", is("Guadeloupe")))
                .andExpect(jsonPath("$.territories.[2].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[2].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[3].territory", is("Martinique")))
                .andExpect(jsonPath("$.territories.[3].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[3].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[4].territory", is("Mayotte")))
                .andExpect(jsonPath("$.territories.[4].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[4].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[5].territory", is("New Caledonia")))
                .andExpect(jsonPath("$.territories.[5].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[5].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[6].territory", is("Reunion")))
                .andExpect(jsonPath("$.territories.[6].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[6].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[7].territory", is("Saint Barthelemy")))
                .andExpect(jsonPath("$.territories.[7].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[7].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[8].territory", is("Saint Pierre and Miquelon")))
                .andExpect(jsonPath("$.territories.[8].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[8].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[9].territory", is("St Martin")))
                .andExpect(jsonPath("$.territories.[9].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[9].deaths_dates", nullValue()))

                .andExpect(jsonPath("$.territories.[10].territory", is("Wallis and Futuna")))
                .andExpect(jsonPath("$.territories.[10].confirmed_dates", notNullValue()))
                .andExpect(jsonPath("$.territories.[10].deaths_dates", nullValue()));

    }

    @Test
    void whenGetCacheStats_thenStatus200() throws Exception {

        mvc.perform(get("/cache").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(containsString("contain_fails")))
                .andExpect(content().string(containsString("contain_successes")))
                .andExpect(content().string(containsString("delete_fails")))
                .andExpect(content().string(containsString("delete_successes")))
                .andExpect(content().string(containsString("get_fails")))
                .andExpect(content().string(containsString("get_successes")))
                .andExpect(content().string(containsString("put_fails")))
                .andExpect(content().string(containsString("put_successes")))
                .andExpect(content().string(containsString("request_count")))
                .andExpect(content().string(containsString("size_requests")));
    }

    @AfterEach
    public void cleanup() {

    }

}
