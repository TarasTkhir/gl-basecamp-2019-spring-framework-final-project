package com.configuration;

import com.utils.AllCharactersMapUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@Configuration
public class ApplConfig {

    @Bean
    public AllCharactersMapUnit allCharacters(){

        return  new AllCharactersMapUnit();
    }
}
