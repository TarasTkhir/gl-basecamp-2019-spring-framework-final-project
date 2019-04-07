package com.controllers;

import com.entity.CharacterEntity;
import com.exception.PersonNotFoundException;
import com.repository.CharacterRepository;
import com.repository.CompareAgeRepository;
import com.service.APICallingServiceImpl;
import com.service.CharacterServiceImpl;
import com.testutils.CreateTestCharacters;
import com.utils.AllCharactersMapUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
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
public class ExceptionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterServiceImpl characterService;

    @MockBean
    private AllCharactersMapUnit allCharactersMapUnit;


    @MockBean
    private APICallingServiceImpl apiCallingService;

    @Test
    public void givenFalseUUID_whenCallFindMapping_thenReturnExceptionWireWithStatusNotFound() throws Exception {

        mockMvc.perform(get("/find/d364b420-8d71-11e3-baa8-0800200c9a55")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("No personage with such ID d364b420-8d71-11e3-baa8-0800200c9a55"));
    }


    @Test
    public void givenTooLongNameThatNotExist_whenCallAddMapping_thenHandleResourceNotFoundExceptionAndReturnStatusBadRequest() throws Exception {

        mockMvc.perform(post("/add/sdfd sfsdfsd gsg rgsgd sgsgsgsdgd gsdfsfsgdd")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("addPersonage.name: Too long or too short name!"));
    }

    @Test
    public void givenPersonWithAbsentBirthYear_whenCallAddMapping_thenHandleNotComparableCharactersExceptionAndReturnBadRequest() throws Exception {

        CharacterEntity testUnit = CreateTestCharacters.generateAndReturnTestCharacterEntity(1);
        testUnit.setBirth_year("EMPTY");
        characterService.getCache().put(testUnit.getName(), testUnit);

        when(allCharactersMapUnit.getAllCharacters()).thenReturn(CreateTestCharacters.generateTestHashMapOfOneMappedCharacter());

        mockMvc.perform(post("/add/Test1")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Cannot compare persons if  age of one of  characters is absent: Test1 And: Test1"));
    }

    @Test
    public void givenUUIDOfResultThatNotExist_whenCallFindResult_thenHandleCompareAgeEntityNotFoundExceptionAndReturnStatusNotFound() throws Exception {

        mockMvc.perform(get("/findResult/d364b420-8d71-11e3-baa8-0800200c9a55")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("No results with such ID: d364b420-8d71-11e3-baa8-0800200c9a55"));
    }
}