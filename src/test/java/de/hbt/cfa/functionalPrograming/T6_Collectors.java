package de.hbt.cfa.functionalPrograming;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class T6_Collectors {

    public static void main(String[] args) {
        List<String> broadwayCards = List.of("A", "K", "Q", "J", "10");
        List<String> cardSymbols = List.of("s", "c", "h", "d");

        List<Integer> integers = List.of(1, 2, 3, 4, 1);

        // collector string joining
        final String resultString =
                broadwayCards.stream().flatMap(s1 -> cardSymbols.stream().map(s2 -> s1 + s2 + " ")).collect(Collectors.joining());
        System.out.println(resultString);

        // integers.stream().collect(Collectors.joining()); nope

        /*  custom collector consist of a supplier (container for our elements)
         *  an accumulator (defines the way to add our elements to the container)
         *  and a combiner which combines the containers into one when parallelStream() is used
         */
        final ArrayList<String> strings =
                integers.stream().collect(ArrayList::new, accumulate(), ArrayList::addAll);
        System.out.println(strings);


    }

    private static BiConsumer<ArrayList<String>, Integer> accumulate() {
        return (container, val) -> {
            System.out.println(container);
            container.add(val.toString());
        };
    }
}
