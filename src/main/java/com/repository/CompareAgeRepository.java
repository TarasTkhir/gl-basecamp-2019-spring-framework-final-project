package com.repository;

import com.entity.CharacterEntity;
import com.entity.CompareAgeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompareAgeRepository extends CrudRepository<CompareAgeEntity, UUID> {

    @Query("select e from CompareAgeEntity e where e.personageName =:personageName and e.randomPersonageName=:randomPersonageName")
    CompareAgeEntity findByNames(@Param("personageName") String personageName,
                                 @Param("randomPersonageName") String randomPersonageName);

    @Query("from CompareAgeEntity order by age_diference DESC")
    List<CompareAgeEntity> getAllCompareAgeResultsOrdered(Pageable pageable);
}
