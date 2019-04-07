package com.controllers;

import com.entity.CharacterEntity;
import com.entity.CompareAgeEntity;
import com.repository.CharacterRepository;
import com.repository.CompareAgeRepository;
import com.service.APICallingService;
import com.service.APICallingServiceImpl;
import com.testutils.CreateTestCharacters;
import com.utils.AllCharactersMapUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class GetCharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterRepository characterRepository;

    @MockBean
    private CompareAgeRepository compareAgeRepository;

    @MockBean
    private AllCharactersMapUnit allCharactersMapUnit;

    @MockBean
    private APICallingServiceImpl apiCallingService;


    @Test
    public void givenMockedPersonageWhenCallDB_whenFindPersonageByIdMappingCalled_thenReturnMockedPersonage() throws Exception {

        UUID uuid9 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a74");


        Optional<CharacterEntity> testUnit = Optional.of(CreateTestCharacters
                .generateAndReturnTestCharacterEntity(9));

        Mockito.when(characterRepository.findById(uuid9)).thenReturn(testUnit);

        this.mockMvc.perform(get("/find/d364b420-8d71-11e3-baa8-0800200c9a74")).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("d364b420-8d71-11e3-baa8-0800200c9a74"))
                .andExpect(jsonPath("$.name").value("Test9"))
                .andExpect(jsonPath("$.birth_year").value("90BBY"));
    }

    @Test
    public void givenMockedPersonageWhenCallDB_whenFindPersonageByIdMappingCalledWithWrongId_thenReturnExceptionWireWithStatusNotFound() throws Exception {

        UUID uuid9 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a74");


        Optional<CharacterEntity> testUnit = Optional.of(CreateTestCharacters
                .generateAndReturnTestCharacterEntity(9));

        Mockito.when(characterRepository.findById(uuid9)).thenReturn(testUnit);

        this.mockMvc.perform(get("/find/d364b420-8d71-11e3-baa8-0800200c9a75")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("No personage with such ID d364b420-8d71-11e3-baa8-0800200c9a75"));
    }


    @Test
    public void givenMockedCompareAgeEntityWhenCallDB_whenFindResultByIdMappingCalled_thenReturnMockedEntity() throws Exception {

        Optional<CompareAgeEntity> testUnit = Optional.of(CreateTestCharacters.generateAndReturnOneTestCompareAgeEntity());

        UUID uuid1 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a80");

        Mockito.when(compareAgeRepository.findById(uuid1)).thenReturn(testUnit);

        this.mockMvc.perform(get("/findResult/d364b420-8d71-11e3-baa8-0800200c9a80")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("d364b420-8d71-11e3-baa8-0800200c9a80"))
                .andExpect(jsonPath("$.ageDiference").value("50"))
                .andExpect(jsonPath("$.olderOne").value("Test2_Older"))
                .andExpect(jsonPath("$.personageAge").value("100BBY"))
                .andExpect(jsonPath("$.randomPersonageAge").value("150BBY"));
    }

    @Test
    public void givenMockedCompareAgeEntityWhenCallDB_whenFindResultByIdMappingCalledWithWrongId_thenReturnExceptionWire() throws Exception {

        Optional<CompareAgeEntity> testUnit = Optional.of(CreateTestCharacters.generateAndReturnOneTestCompareAgeEntity());

        UUID uuid1 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a80");

        Mockito.when(compareAgeRepository.findById(uuid1)).thenReturn(testUnit);

        this.mockMvc.perform(get("/findResult/d364b420-8d71-11e3-baa8-0800200c9a81")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("No results with such ID: d364b420-8d71-11e3-baa8-0800200c9a81"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));
    }

}