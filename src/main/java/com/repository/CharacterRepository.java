package com.repository;

import com.entity.CharacterEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CharacterRepository extends CrudRepository<CharacterEntity, UUID> {

    @Query("select e from CharacterEntity e where e.name =:nameToFind")
    CharacterEntity findPersonByName(@Param("nameToFind") String nameToFind);

    @Query("from CharacterEntity order by name DESC")
    List<CharacterEntity> getAllStarWarsEntityOrdered(Pageable pageable);
}
