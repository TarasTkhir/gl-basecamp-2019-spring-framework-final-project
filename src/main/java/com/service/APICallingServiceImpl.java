package com.service;

import com.entity.CharacterEntity;
import com.exception.PersonNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.wire.AllCharactersWire;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@Log4j2
public class APICallingServiceImpl implements  APICallingService{

    public AllCharactersWire getResponse(String url) {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpResponse<JsonNode> jsonResponse;
        AllCharactersWire allPersonsModel = null;

        try {
            jsonResponse = Unirest.get(url).asJson();
            allPersonsModel = objectMapper
                    .readValue(jsonResponse.getBody().toString(), AllCharactersWire.class);
        } catch (IOException | UnirestException e) {

            log.error(e.getStackTrace());
        }
        log.info(allPersonsModel);

        return allPersonsModel;
    }

    public HashMap<String, Integer> createData() {

        HashMap<String, Integer> personList = new HashMap<>();

        String url = "https://swapi.co/api/people/";

        AllCharactersWire response;

        do {
            response = getResponse(url);

            List<CharacterEntity> results = response.getResults();
            for (CharacterEntity result : results) {
                personList.put(result.getName(),
                        Integer.valueOf(result.getUrl().replaceAll("[^0-9]", "")));
            }
            if (response.getNext() == null) {
                break;
            }
            url = response.getNext();

        } while (true);

        log.info(personList);
        return personList;
    }

    public CharacterEntity getStarWarsEntityFromExternalAPI(int numberOfEntity) {

        ObjectMapper objectMapper = new ObjectMapper();
        CharacterEntity entity;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get("https://swapi.co/api/people/" + numberOfEntity + "/")
                    .asJson();

            entity = objectMapper.readValue(jsonResponse.getBody().toString(), CharacterEntity.class);

        } catch (IOException | UnirestException e) {
            log.info("person with number {} not found" + numberOfEntity);
            throw new PersonNotFoundException("Person not found, or some connection problems. Try again!" +
                    e.getStackTrace());
        }
        return entity;
    }
}
