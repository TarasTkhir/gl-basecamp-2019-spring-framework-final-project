package com.service;

import com.entity.CharacterEntity;
import com.utils.AllCharactersMapUnit;
import com.entity.CompareAgeEntity;
import com.exception.PersonNotFoundException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.repository.CharacterRepository;
import com.utils.UtilsMethods;
import com.wire.PaginationWire;
import com.wire.CharacterResponseWire;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Log4j2
public class CharacterServiceImpl implements CharacterService {

    private CharacterRepository characterRepository;

    private AllCharactersMapUnit allCharacters;

    private ComparedAgeService comparedAgeService;

    private APICallingService apiCallingService;

    private Cache<String, CharacterEntity> cache = Caffeine
            .newBuilder().expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(100)
            .build();

    @Value("${app.host}")
    private String host;

    private final String mapping = "/findAll?page=";

    private Random random = new Random();

    @Autowired
    public void setApiCallingService(APICallingServiceImpl apiCallingServiceImpl) {
        this.apiCallingService = apiCallingServiceImpl;
    }

    @Autowired
    public void setComparedAgeService(ComparedAgeServiceImpl comparedAgeService) {
        this.comparedAgeService = comparedAgeService;
    }

    @Autowired
    public void setAllCharacters(AllCharactersMapUnit allCharacters) {
        this.allCharacters = allCharacters;
    }

    public Cache<String, CharacterEntity> getCache() {
        return this.cache;
    }

    @Autowired
    public void setCharacterRepository(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }


    @Override
    public CharacterResponseWire addEntityAndCompareWithRandomEntityAndReturnIDs(String name) {

        CharacterEntity entity;

        entity = checkAndReturnStarWarsEntityFromCacheOrCreateAndPutInCache(name);

        CompareAgeEntity comparedRandomEntity = comparedAgeService.getCompareAgeEntityAfterComparingOriginalAndRandomEntity(entity);

        return new CharacterResponseWire(HttpStatus.ACCEPTED.toString(), "Personage ID", entity.getId(),
                "Compare result ID", comparedRandomEntity.getId(),
                "Personage " + entity.getName() + " was saved!");
    }


    @Override
    public PaginationWire findAllPagination(Pageable pageable) {

        List<CharacterEntity> resultList = characterRepository.getAllStarWarsEntityOrdered(pageable);

        return UtilsMethods.generateWireAndAddDetails(this.host, pageable, resultList, mapping);
    }


    @Override
    public CharacterEntity findStarWarsEntityById(UUID id) {

        Optional<CharacterEntity> byId = characterRepository.findById(id);
        CharacterEntity entity;

        try {
            entity = byId.get();
        } catch (NoSuchElementException e) {
            log.info("No CharacterEntity was found by id " + id);
            throw new PersonNotFoundException("No personage with such ID " + id);
        }
        return entity;
    }


    @Override
    public CharacterEntity findOrAddEntity(String name) {

        Integer integer = allCharacters.getAllCharacters().get(name);
        CharacterEntity byName = characterRepository
                .findPersonByName(name);
        CharacterEntity entity;
        try {
            if (byName == null) {
                entity = apiCallingService.getStarWarsEntityFromExternalAPI(integer);
                characterRepository.save(entity);
                return entity;
            }
        } catch (NullPointerException e) {
            log.warn("Person with name {} was not found", name);
            throw new PersonNotFoundException("No person with such name!");
        }
        return byName;
    }


    @Override
    public CharacterEntity generateRandomCharacter() {

        Integer randomNumber = random.nextInt(allCharacters.getAllCharacters().size()) + 1;
        HashMap<String, Integer> allCharactersForRandom = this.allCharacters.getAllCharacters();
        String randomCharacterName;
        try {
            randomCharacterName = allCharactersForRandom
                    .entrySet()
                    .stream()
                    .filter(entry -> randomNumber.equals(entry.getValue()))
                    .map(Map.Entry::getKey).findFirst().get();
        } catch (NoSuchElementException e) {

            log.warn("No element was found: {} in map of characters", randomNumber);

            throw new PersonNotFoundException("No random element was found!");
        }

        CharacterEntity entity = checkAndReturnStarWarsEntityFromCacheOrCreateAndPutInCache(randomCharacterName);
        return entity;
    }


    @Override
    public CharacterEntity checkAndReturnStarWarsEntityFromCacheOrCreateAndPutInCache(String name) {

        CharacterEntity entity;

        if (cache.getIfPresent(name) == null) {
            entity = findOrAddEntity(name);
            cache.put(name, entity);

            return entity;

        } else {

            return cache.getIfPresent(name);
        }
    }

    @Override
    public HashMap<String, Integer> getAllCharacters() {
        return this.allCharacters.getAllCharacters();
    }


}
