package com.controllers;


import com.service.CharacterServiceImpl;
import com.wire.CharacterResponseWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

@RestController
@Validated
public class PostCharacterController {

    private CharacterServiceImpl starWarsServiceImpl;

    @Autowired
    public void setStarWarsServiceImpl(CharacterServiceImpl starWarsServiceImpl) {
        this.starWarsServiceImpl = starWarsServiceImpl;
    }

    @PostMapping("/add/{name}")
    public ResponseEntity<CharacterResponseWire> addPersonage(@PathVariable @Size(max = 30,
            message = "Too long or too short name!") String name) {

        return ResponseEntity.accepted().body(starWarsServiceImpl
                .addEntityAndCompareWithRandomEntityAndReturnIDs(name));
    }
}
