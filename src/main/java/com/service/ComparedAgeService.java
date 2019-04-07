package com.service;

import com.entity.CompareAgeEntity;
import com.entity.CharacterEntity;
import com.wire.PaginationWire;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ComparedAgeService {

    CompareAgeEntity findCompareAgeEntityById(UUID id);

    CompareAgeEntity getCompareAgeEntityAfterComparingOriginalAndRandomEntity(CharacterEntity entity);

    CompareAgeEntity checkAndReturnIfCompareAgeEntityIsAlreadyInDatabase(String originalEntity, String randomEntity);

    PaginationWire findAllResultsPagination(Pageable pageable);
}
