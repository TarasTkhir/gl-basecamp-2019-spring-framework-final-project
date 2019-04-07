package com.controllers;


import com.entity.CharacterEntity;
import com.utils.UtilsMethods;
import com.entity.CompareAgeEntity;
import com.service.ComparedAgeService;
import com.service.CharacterService;
import com.service.CharacterServiceImpl;
import com.wire.InstructionWire;
import com.wire.PaginationWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetCharacterController {

    private CharacterService characterServiceImpl;

    private ComparedAgeService comparedAgeService;

    @Autowired
    public void setComparedAgeService(ComparedAgeService comparedAgeService) {
        this.comparedAgeService = comparedAgeService;
    }

    @Autowired
    public void setCharacterServiceImpl(CharacterServiceImpl characterServiceImpl) {
        this.characterServiceImpl = characterServiceImpl;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CharacterEntity> findPersonage(@PathVariable UUID id) {

        return ResponseEntity.accepted().body(characterServiceImpl.findStarWarsEntityById(id));
    }


    @GetMapping("/findAll")
    public ResponseEntity<PaginationWire> findAllPersonages(
            @PageableDefault(value = 3) Pageable pageable) {

        return ResponseEntity.ok().body(characterServiceImpl.findAllPagination(pageable));
    }

    @GetMapping("/findResult/{id}")
    public ResponseEntity<CompareAgeEntity> findResult(@PathVariable UUID id) {

        return ResponseEntity.ok().body(comparedAgeService.findCompareAgeEntityById(id));
    }

    @GetMapping("/")
    public ResponseEntity<InstructionWire> findResult() {

        return ResponseEntity.ok().body(
                new InstructionWire(UtilsMethods.getInstruction(), characterServiceImpl.getAllCharacters()));
    }

    @GetMapping("/findAllResults")
    public ResponseEntity<PaginationWire> findAllResults(
            @PageableDefault(value = 3) Pageable pageable) {
        return ResponseEntity.ok().body(comparedAgeService.findAllResultsPagination(pageable));
    }
}
