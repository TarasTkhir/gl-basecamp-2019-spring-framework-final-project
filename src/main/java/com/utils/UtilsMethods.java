package com.utils;

import com.entity.CharacterEntity;
import com.entity.CompareAgeEntity;
import com.exception.NotComperableCharactersException;
import com.wire.PaginationWire;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;

@Log4j2
public class UtilsMethods {

    private static List<String> infoList = new ArrayList<>();

    public static List<String> getInstruction() {
        if (infoList.isEmpty()) {
            try (BufferedReader bufferedReader =
                         new BufferedReader(
                                 new FileReader(
                                         System.getProperty("user.dir") + "\\src\\main\\resources\\Instruction.txt"))) {
                while (bufferedReader.ready()) {
                    infoList.add(bufferedReader.readLine());
                }
            } catch (IOException e) {

                log.error("Cannot read file: " + e.getStackTrace());
            }
        }
        return infoList;
    }


    public static PaginationWire generateWireAndAddDetails(String host,
                                                           Pageable pageable, List resultList, String mapping) {

        PaginationWire paginationWire = new PaginationWire();
        paginationWire.setNextPage("http://" + host + mapping +
                pageable.next().getPageNumber());
        paginationWire.setPreviousPage("http://" + host + mapping +
                pageable.previousOrFirst().getPageNumber());
        paginationWire.setStartPage("http://" + host + mapping +
                pageable.first().getPageNumber());
        paginationWire.setResult(resultList);

        if (resultList.size() < pageable.next().getPageSize()) {
            paginationWire.setNextPage("N_O____R_E_S_U_L_T_S!");
            return paginationWire;
        }

        return paginationWire;
    }


    public static CompareAgeEntity configureCompareAgeEntity(CharacterEntity originalEntity, CharacterEntity randomEntity) {

        CompareAgeEntity compareAgeEntity = new CompareAgeEntity();
        float personageOriginal;
        float personageRandom;
        try {
            personageOriginal = Float.parseFloat(originalEntity.getBirth_year().replaceAll("BBY", ""));
            personageRandom = Float.parseFloat(randomEntity.getBirth_year().replaceAll("BBY", ""));
        } catch (NullPointerException | NumberFormatException e) {
            log.warn("No age in character {} or character {} was found", originalEntity.getName(), randomEntity.getName());
            throw new NotComperableCharactersException
                    ("Cannot compare persons if  age of one of  characters is absent: "
                            + originalEntity.getName() + " And: " + randomEntity.getName());
        }

        compareAgeEntity.setPersonageAge(" Birth year of " + originalEntity.getName() +
                " is " + originalEntity.getBirth_year());
        compareAgeEntity.setRandomPersonageAge(" Birth year of " + randomEntity.getName() +
                " is " + randomEntity.getBirth_year());
        compareAgeEntity.setAgeDiference("Difference is " + Math.abs((int) (personageOriginal - personageRandom)));
        compareAgeEntity.setPersonageName(originalEntity.getName());
        compareAgeEntity.setRandomPersonageName(randomEntity.getName());


        if (personageOriginal > personageRandom) {
            compareAgeEntity.setOlderOne("Older one is: " + originalEntity.getName() +
                    ". He is older than: " + randomEntity.getName());
        } else if (personageOriginal < personageRandom) {
            compareAgeEntity.setOlderOne("Older one is: " + randomEntity.getName() +
                    ". He is older than: " + originalEntity.getName());
        } else if (personageOriginal == personageRandom) {
            compareAgeEntity.setOlderOne("Same age!!Is it same character?");
        }
        return compareAgeEntity;
    }
}
