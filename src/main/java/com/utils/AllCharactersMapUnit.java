package com.utils;


import com.service.APICallingService;
import com.service.APICallingServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;


@EnableScheduling
@Log4j2
public class AllCharactersMapUnit {

    private HashMap<String, Integer> allCharacters;

    private APICallingService apiCallingService;


    @Autowired
    public void setApiCallingService(APICallingServiceImpl apiCallingServiceImpl) {
        this.apiCallingService = apiCallingServiceImpl;
    }

    public HashMap<String, Integer> getAllCharacters() {
        return this.allCharacters;
    }


    @Scheduled(fixedDelay = 1000000)//16 minutes
    public void setAllCharacters() {
        try {
            this.allCharacters = apiCallingService.createData();

        } catch (NullPointerException e) {

            log.warn("Cannot initialise map");
        }
    }
}
