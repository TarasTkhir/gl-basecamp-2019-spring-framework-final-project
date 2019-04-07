package com.controllers;

import com.entity.CharacterEntity;
import com.repository.CharacterRepository;
import com.service.APICallingServiceImpl;
import com.service.CharacterServiceImpl;
import com.testutils.CreateTestCharacters;
import com.utils.AllCharactersMapUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class PostCharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterServiceImpl characterService;

    @MockBean
    private AllCharactersMapUnit allCharactersMapUnit;

    @MockBean
    private APICallingServiceImpl apiCallingService;

    @Test
    public void givenTestCharacterAndMockedMap_whenAddTestCharacterByNameMappingCalled_thenReturnCharacterResponseWireWithCharacterId() throws Exception {

        CharacterEntity testUnit = CreateTestCharacters.generateAndReturnTestCharacterEntity(1);
        testUnit.setBirth_year("10BBY");
        characterService.getCache().put(testUnit.getName(), testUnit);
        Mockito.when(allCharactersMapUnit.getAllCharacters()).thenReturn(CreateTestCharacters
                .generateTestHashMapOfOneMappedCharacter());


        this.mockMvc.perform(post("/add/Test1")).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("d364b420-8d71-11e3-baa8-0800200c9a66"))
                .andExpect(jsonPath("$.message").value("Personage Test1 was saved!"))
                .andExpect(jsonPath("$.status").value("202 ACCEPTED"));
        characterRepository.deleteAll();
    }

    @Test
    public void givenTestCharacterAndMockedMap_whenAddCharacterWithNotExistingNameInAddMapping_thenReturnExceptionWireAndStatusNotFound() throws Exception {

        Mockito.when(allCharactersMapUnit.getAllCharacters()).thenReturn(CreateTestCharacters
                .generateTestHashMapOfOneMappedCharacter());
        Mockito.when(apiCallingService.getStarWarsEntityFromExternalAPI(1))
                .thenReturn(CreateTestCharacters.generateAndReturnTestCharacterEntity(1));

        this.mockMvc.perform(post("/add/Test2")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("No person with such name!"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));

    }
}