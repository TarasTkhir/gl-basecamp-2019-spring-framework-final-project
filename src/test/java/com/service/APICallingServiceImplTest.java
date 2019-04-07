package com.service;

import com.entity.CharacterEntity;
import com.exception.PersonNotFoundException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class APICallingServiceImplTest {

    APICallingService apiCallingService = new APICallingServiceImpl();

    @Test
    public void givenCreateDataFromExternalIPIAndPutIntoMap_whenGetDataByName_thenReturnWriteIdOfPersonage() {

        HashMap<String, Integer> data = apiCallingService.createData();

        Integer number = 1;
        Integer number1 = 20;

        assertEquals(number, data.get("Luke Skywalker"));
        assertEquals(number1, data.get("Yoda"));
    }

    @Test
    public void givenCreateDataFromExternalIPIAndPutIntoMap_whenGetDataByNotExistingName_thenReturnNull() {

        HashMap<String, Integer> data = apiCallingService.createData();

        assertEquals(null, data.get("uahduiah"));

    }

    @Test
    public void givenGetStarWarsEntityFromExternalAPI_whenGetCharacterById_thenReturnExpectedCharacter() {

        CharacterEntity starWarsEntityFromExternalAPI = apiCallingService.getStarWarsEntityFromExternalAPI(1);

        assertEquals("Luke Skywalker", starWarsEntityFromExternalAPI.getName());
        assertEquals("172", starWarsEntityFromExternalAPI.getHeight());
        assertEquals("77", starWarsEntityFromExternalAPI.getMass());
        assertEquals("19BBY", starWarsEntityFromExternalAPI.getBirth_year());
    }

    @Test
    public void givenGetStarWarsEntityFromExternalAPI_whenGetCharacterByNotExistingId_thenReturnEmptyCharacter() {

        CharacterEntity starWarsEntityFromExternalAPI = apiCallingService.getStarWarsEntityFromExternalAPI(100000);
        assertEquals(null, starWarsEntityFromExternalAPI.getName());
        assertEquals(null, starWarsEntityFromExternalAPI.getBirth_year());
        assertEquals(null, starWarsEntityFromExternalAPI.getMass());
        assertEquals(null, starWarsEntityFromExternalAPI.getHeight());
        assertEquals(null, starWarsEntityFromExternalAPI.getEdited());


    }
}