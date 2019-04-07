package com.service;

import com.entity.CharacterEntity;
import com.wire.PaginationWire;
import com.wire.CharacterResponseWire;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.UUID;

public interface CharacterService {

    CharacterResponseWire addEntityAndCompareWithRandomEntityAndReturnIDs(String name);

    PaginationWire findAllPagination(Pageable pageable);

    CharacterEntity findStarWarsEntityById(UUID id);

    CharacterEntity findOrAddEntity(String name);

    CharacterEntity generateRandomCharacter();

    CharacterEntity checkAndReturnStarWarsEntityFromCacheOrCreateAndPutInCache(String name);

    HashMap<String, Integer> getAllCharacters();


}
