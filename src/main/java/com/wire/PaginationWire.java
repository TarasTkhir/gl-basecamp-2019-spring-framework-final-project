package com.wire;

import com.entity.CharacterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaginationWire {

    private String nextPage;

    private String previousPage;

    private String startPage;

    private List<?> result;


}
