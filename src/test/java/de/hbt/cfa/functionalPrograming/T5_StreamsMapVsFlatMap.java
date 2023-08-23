package de.hbt.cfa.functionalPrograming;

import java.util.List;

public class T5_StreamsMapVsFlatMap {

    public static void main(String[] args) {
        List<SomeRecord> someRecords = List.of(new SomeRecord(1L, "a"), new SomeRecord(2L, "b"));
        final List<Long> resultListLong = someRecords.stream().map(SomeRecord::aLong).toList();
        System.out.println(resultListLong);

        List<List<SomeRecord>> nestedList = List.of(someRecords);
        final List<String> resultListString = nestedList.stream()
                .flatMap(someRecords1 -> someRecords1.stream().map(record -> record.aString))
                /*  .flatMap(Collection::stream)
                    .map(SomeRecord::aString)
                **/
                .toList();
        System.out.println(resultListString);
    }

    private record SomeRecord(Long aLong, String aString) {}
}
