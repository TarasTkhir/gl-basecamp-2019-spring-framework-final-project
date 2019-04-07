package com.wire;

import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CharacterResponseWire {

    private String status;

    private String personageId;

    private UUID id;

    private String comparedResultId;

    private UUID resultId;

    private String message;
}
