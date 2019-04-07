package com.utils;

import com.service.APICallingServiceImpl;
import com.testutils.CreateTestCharacters;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AllCharactersMapUnitTest {

    @InjectMocks
    private AllCharactersMapUnit allCharactersMapUnit;

    @Mock
    private APICallingServiceImpl apiCallingService;

    @Before
    public void setUp() {
        apiCallingService = Mockito.mock(APICallingServiceImpl.class);
        allCharactersMapUnit = new AllCharactersMapUnit();
    }


    @Test
    public void givenMockedCharactersMap_whenAllCharacterMapUnitSetAllCharacters_thenReturnMockedCharacterMap() {

        //given
        when(apiCallingService.createData()).thenReturn(CreateTestCharacters.generateTestHashMapOfManyMappedCharacters());
        this.allCharactersMapUnit.setApiCallingService(this.apiCallingService);

        //when
        allCharactersMapUnit.setAllCharacters();

        //then
        assertEquals(CreateTestCharacters.generateTestHashMapOfManyMappedCharacters()
                .size(), allCharactersMapUnit.getAllCharacters().size());
    }

    @Test
    public void givenMockedCreateDataByNull_whenAllCharacterMapUnitSetAllCharacters_thenReturnNull() {

        //given
        when(apiCallingService.createData()).thenReturn(null);
        this.allCharactersMapUnit.setApiCallingService(this.apiCallingService);

        // when
        allCharactersMapUnit.setAllCharacters();

        //then
        assertEquals(null, allCharactersMapUnit.getAllCharacters());
    }
}