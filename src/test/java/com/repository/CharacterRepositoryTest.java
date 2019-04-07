package com.repository;

import com.entity.CharacterEntity;
import com.testutils.CreateTestCharacters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CharacterRepositoryTest {

    private Pageable pageable;

    @Autowired
    private CharacterRepository characterRepository;

    @Test
    public void givenTestUnit_whenFindByName_thenReturnSameTestUnit() {
        // given
        CharacterEntity TestUnit = CreateTestCharacters.generateAndReturnTestCharacterEntity(10);

        characterRepository.save(TestUnit);
        // when
        CharacterEntity found = characterRepository.findPersonByName(TestUnit.getName());

        // then
        assertEquals(TestUnit.getName(), found.getName());

        characterRepository.deleteAll();
    }

    @Test
    public void givenTestUnit_whenFindByName_thenReturnSameTestUnit_Neg() {
        // given
        CharacterEntity TestUnit = CreateTestCharacters.generateAndReturnTestCharacterEntity(10);

        characterRepository.save(TestUnit);
        // when
        CharacterEntity found = characterRepository.findPersonByName(TestUnit.getName());

        // then
        assertEquals("120", found.getHeight());
        assertNotEquals("", found.getName());

        characterRepository.deleteAll();
    }

    @Test
    public void givenTwoTestUnitsAndSaveToDB_whenGetAllStarWarsEntityOrdered_thenReturnOrderedDESCUnits() {
        // given
        CharacterEntity TestUnit1 = CreateTestCharacters.generateAndReturnTestCharacterEntity(10);
        CharacterEntity TestUnit2 = CreateTestCharacters.generateAndReturnTestCharacterEntity(1);

        characterRepository.save(TestUnit2);
        characterRepository.save(TestUnit1);
        // when
        List<CharacterEntity> allStarWarsEntityOrdered = characterRepository.getAllStarWarsEntityOrdered(pageable);

        // then
        assertEquals("Test10", allStarWarsEntityOrdered.get(0).getName());
        assertEquals("Test1", allStarWarsEntityOrdered.get(1).getName());

        characterRepository.deleteAll();
    }

    @Test
    public void givenTwoTestUnitsAndSaveToDB_whenGetAllStarWarsEntityOrdered_thenReturnOrderedDESCUnits_Negative() {
        // given
        CharacterEntity TestUnit1 = CreateTestCharacters.generateAndReturnTestCharacterEntity(10);
        CharacterEntity TestUnit2 = CreateTestCharacters.generateAndReturnTestCharacterEntity(1);

        characterRepository.save(TestUnit1);
        characterRepository.save(TestUnit2);
        // when

        List<CharacterEntity> allStarWarsEntityOrdered = characterRepository.getAllStarWarsEntityOrdered(pageable);

        // then
        assertNotEquals("Test1", allStarWarsEntityOrdered.get(0).getName());
        assertNotEquals("Test10", allStarWarsEntityOrdered.get(1).getName());

        characterRepository.deleteAll();
    }
}