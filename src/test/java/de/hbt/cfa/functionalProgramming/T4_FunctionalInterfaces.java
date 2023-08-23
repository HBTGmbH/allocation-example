package de.hbt.cfa.functionalProgramming;

import de.hbt.cfa.entity.CurryingLong;
import de.hbt.cfa.entity.CurryingString;

public class T4_FunctionalInterfaces {

    public static void main(String[] args) {
        final SomeRecord someRecord = curry()
                .longVal(11L)
                .longVal(7L)
                .stringVal("aString");
        System.out.println(someRecord);
    }

    static CurryingLong<CurryingLong<CurryingString<SomeRecord>>> curry() {
        return l1 -> l2 -> s1 -> new SomeRecord(l1, l2, s1);
    }

    record SomeRecord(Long l1, Long l2, String s1) {}
}
