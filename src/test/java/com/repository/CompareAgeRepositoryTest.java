package com.repository;

import com.entity.CompareAgeEntity;
import com.testutils.CreateTestCharacters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompareAgeRepositoryTest {


    private Pageable pageable;

    @Autowired
    private CompareAgeRepository compareAgeRepository;

    @Test
    public void givenTwoCompareAgeTestUnitsAndSaveInDB_whenFindByNames_thenReturnUnitWithSameNamesAsTestUnit() {
        // given
        CompareAgeEntity testUnit1 = CreateTestCharacters.generateAndReturnTestCompareAgeEntity(1);
        CompareAgeEntity testUnit2 = CreateTestCharacters.generateAndReturnTestCompareAgeEntity(2);

        compareAgeRepository.save(testUnit1);
        compareAgeRepository.save(testUnit2);

        // when
        CompareAgeEntity byNames = compareAgeRepository.findByNames(
                testUnit1.getPersonageName(), testUnit1.getRandomPersonageName());

        // then
        assertEquals(testUnit1.getPersonageName(), byNames.getPersonageName());
        assertEquals(testUnit1.getRandomPersonageName(), byNames.getRandomPersonageName());

        compareAgeRepository.deleteAll();
    }

    @Test
    public void givenTwoCompareAgeTestUnitsAndSaveInDB_whenFindByNames_thenReturnUnitWithSameNamesAsTestUnit_Negative() {
        // given
        CompareAgeEntity testUnit1 = CreateTestCharacters.generateAndReturnTestCompareAgeEntity(1);
        CompareAgeEntity testUnit2 = CreateTestCharacters.generateAndReturnTestCompareAgeEntity(2);

        compareAgeRepository.save(testUnit1);
        compareAgeRepository.save(testUnit2);
        // when
        CompareAgeEntity byNames = compareAgeRepository.findByNames(
                testUnit1.getPersonageName(), testUnit1.getRandomPersonageName());

        // then
        assertNotEquals(testUnit2.getPersonageName(), byNames.getPersonageName());
        assertNotEquals(testUnit2.getRandomPersonageName(), byNames.getRandomPersonageName());

        compareAgeRepository.deleteAll();
    }

    @Test
    public void givenFourCompareAgeTestUnitsSavedInDB_whenGetAllCompareAgeResultsOrdered_thenReturnCompareAgeTestUnitsOrderedByAgeDiffDESC() {
        // given

        CreateTestCharacters.saveManyCompareAgeEntity(compareAgeRepository);

        // when
        List<CompareAgeEntity> allCompareAgeResultsOrdered = compareAgeRepository.getAllCompareAgeResultsOrdered(pageable);

        // then
        assertEquals("90", allCompareAgeResultsOrdered.get(0).getAgeDiference());
        assertEquals("75", allCompareAgeResultsOrdered.get(1).getAgeDiference());
        assertEquals("50", allCompareAgeResultsOrdered.get(2).getAgeDiference());
        assertEquals("10", allCompareAgeResultsOrdered.get(3).getAgeDiference());

        compareAgeRepository.deleteAll();
    }

    @Test
    public void givenEmptyDB_whenGetAllCompareAgeResultsOrdered_thenReturnEmptyList() {
        // given


        // when
        List<CompareAgeEntity> allCompareAgeResultsOrdered = compareAgeRepository.getAllCompareAgeResultsOrdered(pageable);

        // then
        assertEquals(0, allCompareAgeResultsOrdered.size());


    }

}