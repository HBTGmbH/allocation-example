package de.hbt.cfa.functionalProgramming;

import java.util.function.Function;

public class T3_Currying {

    public static void main(String[] args) {
        final String curried = spiceMe()
                .apply(2L)
                .apply(2L)
                .apply(2L);
        System.out.println(curried);
    }

    static Function<Long, Function<Long, Function<Long, String>>> spiceMe() {
        return l1 -> l2 -> l3 -> String.valueOf(l1 * l2 * l3);
    }
}
