package com.wire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
public class InstructionWire {

    private List<String> instruction;

    private HashMap<String,Integer> allCharacters;
}
