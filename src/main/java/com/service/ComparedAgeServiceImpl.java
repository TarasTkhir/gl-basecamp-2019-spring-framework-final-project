package com.service;

import com.entity.CompareAgeEntity;
import com.entity.CharacterEntity;
import com.exception.CompareAgeEntityNotFound;
import com.repository.CompareAgeRepository;
import com.utils.UtilsMethods;
import com.wire.PaginationWire;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Integer.valueOf;

@Service
@Log4j2
public class ComparedAgeServiceImpl implements ComparedAgeService {

    private CharacterService characterServiceImpl;

    private CompareAgeRepository compareAgeRepository;

    @Value("${app.host}")
    private String host;

    private final String mapping = "/findAllResults?page=";

    @Autowired
    public void setCompareAgeRepository(CompareAgeRepository compareAgeRepository) {
        this.compareAgeRepository = compareAgeRepository;
    }

    @Autowired
    public void setCharacterServiceImpl(CharacterService characterServiceImpl) {
        this.characterServiceImpl = characterServiceImpl;
    }

    @Override
    public CompareAgeEntity findCompareAgeEntityById(UUID id) {

        Optional<CompareAgeEntity> byId = compareAgeRepository.findById(id);
        CompareAgeEntity ageCompareEntity;

        try {
            ageCompareEntity = byId.get();
        } catch (NoSuchElementException e) {
            log.info("No CompareAgeEntity was found by id " + id);
            throw new CompareAgeEntityNotFound("No results with such ID: " + id);
        }
        return ageCompareEntity;
    }


    @Override
    public CompareAgeEntity getCompareAgeEntityAfterComparingOriginalAndRandomEntity(CharacterEntity originalEntity) {

        CharacterEntity randomEntity = characterServiceImpl.generateRandomCharacter();

        CompareAgeEntity compareAgeEntity =
                checkAndReturnIfCompareAgeEntityIsAlreadyInDatabase(originalEntity.getName(), randomEntity.getName());

        if (compareAgeEntity != null) {
            return compareAgeEntity;
        }

        CompareAgeEntity compareAgeEntityConfigured = UtilsMethods.configureCompareAgeEntity(originalEntity, randomEntity);

        compareAgeRepository.save(compareAgeEntityConfigured);

        return compareAgeEntityConfigured;
    }


    @Override
    public CompareAgeEntity checkAndReturnIfCompareAgeEntityIsAlreadyInDatabase
            (String originalEntity, String randomEntity) {

        CompareAgeEntity byNames = compareAgeRepository.findByNames(originalEntity, randomEntity);
        CompareAgeEntity byNames1 = compareAgeRepository.findByNames(randomEntity, originalEntity);
        if (byNames != null) {
            return byNames;
        }
        if (byNames1 != null) {
            return byNames1;
        }

        return null;
    }


    @Override
    public PaginationWire findAllResultsPagination(Pageable pageable) {

        List<CompareAgeEntity> allResultList = compareAgeRepository.getAllCompareAgeResultsOrdered(pageable);
        return UtilsMethods.generateWireAndAddDetails(this.host, pageable, allResultList,
                mapping);
    }
}
