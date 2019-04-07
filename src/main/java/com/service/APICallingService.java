package com.service;

import com.entity.CharacterEntity;
import com.wire.AllCharactersWire;

import java.util.HashMap;

public interface APICallingService {

     AllCharactersWire getResponse(String url);

     HashMap<String, Integer> createData();

    CharacterEntity getStarWarsEntityFromExternalAPI(int numberOfEntity);
}
