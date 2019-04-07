package com.wire;

import com.entity.CharacterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AllCharactersWire {

    private String count;

    private String next;

    private String previous;

    private List<CharacterEntity> results;

    @Override
    public String toString() {
        return "AllCharactersWire{" +
                "count='" + count + '\'' +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}
