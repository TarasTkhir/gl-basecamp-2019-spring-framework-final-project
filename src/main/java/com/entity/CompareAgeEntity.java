package com.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompareAgeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String personageName;

    @Column
    private String randomPersonageName;

    @Column
    private String personageAge;

    @Column
    private String randomPersonageAge;

    @Column
    private String ageDiference;

    @Column
    private String olderOne;

    public CompareAgeEntity(String personageName, String randomPersonageName,
                            String personageAge, String randomPersonageAge,
                            String ageDiference, String olderOne) {
        this.personageName = personageName;
        this.randomPersonageName = randomPersonageName;
        this.personageAge = personageAge;
        this.randomPersonageAge = randomPersonageAge;
        this.ageDiference = ageDiference;
        this.olderOne = olderOne;
    }
}
