package com.controllers;

import com.entity.CharacterEntity;
import com.repository.CharacterRepository;
import com.repository.CompareAgeRepository;
import com.service.APICallingServiceImpl;
import com.testutils.CreateTestCharacters;
import com.utils.AllCharactersMapUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class GetCharacterController1Test {

    private WebApplicationContext webApplicationContext;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CompareAgeRepository compareAgeRepository;

    @MockBean
    AllCharactersMapUnit allCharactersMapUnit;

    @MockBean
    APICallingServiceImpl apiCallingService;

    @Test
    public void givenWebApplicationContext_whenServletContext_thenItProvidesAllCharactersBean() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("allCharacters"));
    }

    @Test
    public void givenTenTestEntitySavedOnDB_whenCallFindAllMapping_thenReturnPaginationWireWithFewEntityAndLinkToNextPage() throws Exception {

        List<CharacterEntity> listOfTestEntity = CreateTestCharacters.getListOfTestEntitys();
        for (CharacterEntity cr :
                listOfTestEntity) {
            characterRepository.save(cr);
        }

        this.mockMvc.perform(get("/findAll")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.nextPage").value("http://localhost:8080/findAll?page=1"))
                .andExpect(jsonPath("$.previousPage").value("http://localhost:8080/findAll?page=0"))
                .andExpect(jsonPath("$.startPage").value("http://localhost:8080/findAll?page=0"))
                .andExpect(jsonPath("$.result").isNotEmpty())
                .andExpect(jsonPath("$.result").isArray());

        characterRepository.deleteAll();
    }

    @Test
    public void givenZeroTestEntitySavedOnDB_whenCallFindAllMapping_thenReturnPaginationWireWithNoEntityAndNoLinkToNextPage() throws Exception {

        this.mockMvc.perform(get("/findAll")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.nextPage").value("N_O____R_E_S_U_L_T_S!"))
                .andExpect(jsonPath("$.previousPage").value("http://localhost:8080/findAll?page=0"))
                .andExpect(jsonPath("$.startPage").value("http://localhost:8080/findAll?page=0"))
                .andExpect(jsonPath("$.result").isEmpty())
                .andExpect(jsonPath("$.result").isArray());

    }

    @Test
    public void givenFourTestResultEntitySavedOnDB_whenCallFindAllResultsMapping_thenReturnPaginationWireWithFewEntityAndLinkToNextPage() throws Exception {

        CreateTestCharacters.saveManyCompareAgeEntity(compareAgeRepository);

        this.mockMvc.perform(get("/findAllResults")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.nextPage").value("http://localhost:8080/findAllResults?page=1"))
                .andExpect(jsonPath("$.previousPage").value("http://localhost:8080/findAllResults?page=0"))
                .andExpect(jsonPath("$.startPage").value("http://localhost:8080/findAllResults?page=0"))
                .andExpect(jsonPath("$.result").isNotEmpty())
                .andExpect(jsonPath("$.result").isArray());
        compareAgeRepository.deleteAll();
    }

    @Test
    public void givenZeroTestResultEntitySavedOnDB_whenCallFindAllResultsMapping_thenReturnPaginationWireWithNoResultEntityAndNoLinkToNextPage() throws Exception {

        this.mockMvc.perform(get("/findAllResults")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.nextPage").value("N_O____R_E_S_U_L_T_S!"))
                .andExpect(jsonPath("$.previousPage").value("http://localhost:8080/findAllResults?page=0"))
                .andExpect(jsonPath("$.startPage").value("http://localhost:8080/findAllResults?page=0"))
                .andExpect(jsonPath("$.result").isEmpty())
                .andExpect(jsonPath("$.result").isArray());
    }

    @Test
    public void givenMockedMapOfCharacters_whenStartPageMapping_thenReturnInstructionAndMockedMapOfCharacters() throws Exception {

        Mockito.when(allCharactersMapUnit.getAllCharacters()).thenReturn(CreateTestCharacters
                .generateTestHashMapOfManyMappedCharacters());

        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.instruction").isArray())
                .andExpect(jsonPath("$.instruction").isNotEmpty())
                .andExpect(jsonPath("$.allCharacters").isMap())
                .andExpect(jsonPath("$.allCharacters").isNotEmpty());
        compareAgeRepository.deleteAll();
    }
}