package de.hbt.cfa.functionalPrograming;

import de.hbt.cfa.entity.SomeValueObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class T8_StreamGroupBy {

    public static void main(String[] args) {
        // demonstrate the use of Stream.collect() with Collectors.groupingBy()

        Map<String, List<SomeValueObject>> collectedToMap = Stream.of(new SomeValueObject("a", "b"), new SomeValueObject("a", "c"), new SomeValueObject("b", "c"), new SomeValueObject("b", "d"))
                .collect(Collectors.groupingBy(SomeValueObject::getFirstStringVal));
        collectedToMap.forEach((key, value) -> System.out.printf("key: %s, values: %s%n", key, value));
    }
}
