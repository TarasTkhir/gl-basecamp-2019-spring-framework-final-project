package com.testutils;

import com.entity.CharacterEntity;
import com.entity.CompareAgeEntity;
import com.repository.CompareAgeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CreateTestCharacters {

    public static HashMap<String, Integer> generateTestHashMapOfOneMappedCharacter() {

        HashMap<String, Integer> testMap = new HashMap<>();

        testMap.put("Test1", 1);

        return testMap;
    }


    public static HashMap<String, Integer> generateTestHashMapOfManyMappedCharacters() {

        HashMap<String, Integer> testMap = new HashMap<>();
        testMap.put("Test1", 1);
        testMap.put("Test2", 2);
        testMap.put("Test3", 3);
        testMap.put("Test4", 4);
        testMap.put("Test5", 5);
        testMap.put("Test6", 6);
        testMap.put("Test7", 7);
        testMap.put("Test8", 8);
        testMap.put("Test9", 9);
        testMap.put("Test10", 10);


        return testMap;
    }


    public static List<CharacterEntity> getListOfTestEntitys() {

        List<CharacterEntity> testResults = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            testResults.add(CreateTestCharacters.generateAndReturnTestCharacterEntity(i));
        }
        return testResults;
    }


    public static CompareAgeEntity generateAndReturnOneTestCompareAgeEntity() {

        UUID uuid1 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a80");

        return new CompareAgeEntity(uuid1, "Test1",
                "Test2", "100BBY", "150BBY",
                "50", "Test2_Older");
    }


    public static void saveManyCompareAgeEntity(CompareAgeRepository compareAgeRepository) {
        compareAgeRepository.save(new CompareAgeEntity("Test1",
                "Test2", "10BBY", "15BBY",
                "50", "Test2_Older"));
        compareAgeRepository.save(new CompareAgeEntity("Test2",
                "Test2", "100BBY", "150BBY",
                "10", "Test2_Older"));
        compareAgeRepository.save(new CompareAgeEntity("Test3",
                "Test2", "100BBY", "150BBY",
                "90", "Test2_Older"));
        compareAgeRepository.save(new CompareAgeEntity("Test4",
                "Test2", "100BBY", "150BBY",
                "75", "Test2_Older"));
    }


    public static CompareAgeEntity generateAndReturnTestCompareAgeEntity(int numberOfEntity) {

        switch (numberOfEntity) {
            case 1:
                return new CompareAgeEntity(null,
                        "Harry", "NotHarry", "x",
                        "y", "50", "z");
            case 2:
                return new CompareAgeEntity(null,
                        "Harry1", "NotHarry1", "x",
                        "y", "50", "z");
            default:
                return null;
        }
    }

    public static CharacterEntity generateAndReturnTestCharacterEntity(int numberOfEntity) {

        UUID uuid1 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a66");
        UUID uuid2 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a67");
        UUID uuid3 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a68");
        UUID uuid4 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a69");
        UUID uuid5 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a70");
        UUID uuid6 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a71");
        UUID uuid7 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a72");
        UUID uuid8 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a73");
        UUID uuid9 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a74");
        UUID uuid10 = UUID.fromString("d364b420-8d71-11e3-baa8-0800200c9a75");

        switch (numberOfEntity) {
            case 1:
                return new CharacterEntity(uuid1, "Test1", "120",
                        "120", "120", "120", "120", "10BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 2:
                return new CharacterEntity(uuid2, "Test2", "120",
                        "120", "120", "120", "120", "20BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 3:
                return new CharacterEntity(uuid3, "Test3", "120",
                        "120", "120", "120", "120", "30BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 4:
                return new CharacterEntity(uuid4, "Test4", "120",
                        "120", "120", "120", "120", "40BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 5:
                return new CharacterEntity(uuid5, "Test5", "120",
                        "120", "120", "120", "120", "x",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 6:
                return new CharacterEntity(uuid6, "Test6", "120",
                        "120", "120", "120", "120", "60BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 7:
                return new CharacterEntity(uuid7, "Test7", "120",
                        "120", "120", "120", "120", "70BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 8:
                return new CharacterEntity(uuid8, "Test8", "120",
                        "120", "120", "120", "120", "80BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 9:
                return new CharacterEntity(uuid9, "Test9", "120",
                        "120", "120", "120", "120", "90BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            case 10:
                return new CharacterEntity(uuid10, "Test10", "120",
                        "120", "120", "120", "120", "100BBY",
                        "120", "120", new String[0], new String[0],
                        new String[0], new String[0], "120", "120", "120", "120");
            default:
                return null;
        }
    }
}
