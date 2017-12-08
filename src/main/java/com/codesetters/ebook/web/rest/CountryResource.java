package com.codesetters.ebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.ebook.service.CountryService;
import com.codesetters.ebook.service.dto.CityDTO;
import com.codesetters.ebook.service.dto.CountriesDTO;
import com.codesetters.ebook.service.dto.StateDTO;
import com.codesetters.ebook.web.rest.util.HeaderUtil;
import com.codesetters.ebook.service.dto.CountryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Country.
 */
@RestController
@RequestMapping("/api")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private static final String ENTITY_NAME = "country";

    private final CountryService countryService;

    private static final String countryUrl = "https://battuta.medunes.net/api/country/all/?key=98b82349f94aa88ee31f7eaab7919ea8";

    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * POST  /countries : Create a new country.
     *
     * @param countryDTO the countryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countryDTO, or with status 400 (Bad Request) if the country has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/countries")
    @Timed
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO countryDTO) throws URISyntaxException {
        log.debug("REST request to save Country : {}", countryDTO);
        if (countryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new country cannot already have an ID")).body(null);
        }
        CountryDTO result = countryService.save(countryDTO);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /countries : Updates an existing country.
     *
     * @param countryDTO the countryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countryDTO,
     * or with status 400 (Bad Request) if the countryDTO is not valid,
     * or with status 500 (Internal Server Error) if the countryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/countries")
    @Timed
    public ResponseEntity<CountryDTO> updateCountry(@RequestBody CountryDTO countryDTO) throws URISyntaxException {
        log.debug("REST request to update Country : {}", countryDTO);
        if (countryDTO.getId() == null) {
            return createCountry(countryDTO);
        }
        CountryDTO result = countryService.save(countryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /countries : get all the countries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countries in body
     */
    @GetMapping("/countries")
    @Timed
    public List<CountryDTO> getAllCountries() {
        log.debug("REST request to get all Countries");
        return countryService.findAll();
    }

    /**
     * GET  /countries/:id : get the "id" country.
     *
     * @param id the id of the countryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/countries/{id}")
    @Timed
    public ResponseEntity<CountryDTO> getCountry(@PathVariable String id) {
        log.debug("REST request to get Country : {}", id);
        CountryDTO countryDTO = countryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(countryDTO));
    }

    @GetMapping("/countries-external")
    @Timed
    public List<CountryDTO> getCountriesExternal() throws JSONException {
        RestTemplate template = new RestTemplate();
        String response = template.getForObject(countryUrl, String.class);

        List<CountryDTO> countriesDTOList = new ArrayList<>();
        JSONArray responseArray = new JSONArray(response);
        for (int i = 0; i < responseArray.length(); i++) {
            CountryDTO countryDTO = new CountryDTO();
            JSONObject resp = responseArray.getJSONObject(i);
            countryDTO.setCountryname(resp.getString("name"));
            countryDTO.setCountrycode(resp.getString("code"));
            countriesDTOList.add(countryDTO);
        }
        return countriesDTOList;
    }

    @GetMapping("/state/{countrycode}")
    @Timed
    public List<StateDTO> getStatesExternal(@PathVariable String countrycode) throws JSONException {
        String url = "https://battuta.medunes.net/api/region/" + countrycode + "/all/?key=98b82349f94aa88ee31f7eaab7919ea8";
        RestTemplate template = new RestTemplate();
        String response = template.getForObject(url, String.class);

        List<StateDTO> stateDTOList = new ArrayList<>();
        JSONArray responseArray = new JSONArray(response);
        for (int i = 0; i < responseArray.length(); i++) {
            StateDTO stateDTO = new StateDTO();
            JSONObject resp = responseArray.getJSONObject(i);
            stateDTO.setStatename(resp.getString("region"));
            stateDTO.setCountrycode(resp.getString("country"));
            stateDTOList.add(stateDTO);
        }
        return stateDTOList;
    }

    @GetMapping("/city/{countrycode}/{state}")
    @Timed
    public List<CityDTO> getCitiesExternal(@PathVariable String countrycode,@PathVariable String state) throws JSONException {
        String url = "https://battuta.medunes.net/api/city/"+countrycode+"/search/?region="+state+"&key=98b82349f94aa88ee31f7eaab7919ea8";
        RestTemplate template = new RestTemplate();
        String response = template.getForObject(url, String.class);

        List<CityDTO> cityDTOList = new ArrayList<>();
        JSONArray responseArray = new JSONArray(response);
        for (int i = 0; i < responseArray.length(); i++) {
            CityDTO cityDTO=new CityDTO();
            JSONObject resp = responseArray.getJSONObject(i);
            cityDTO.setCityname(resp.getString("city"));
            cityDTOList.add(cityDTO);
        }
        return cityDTOList;
    }

    /**
     * DELETE  /countries/:id : delete the "id" country.
     *
     * @param id the id of the countryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/countries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCountry(@PathVariable String id) {
        log.debug("REST request to delete Country : {}", id);
        countryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
