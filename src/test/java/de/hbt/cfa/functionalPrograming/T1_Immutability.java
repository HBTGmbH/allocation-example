package de.hbt.cfa.functionalPrograming;

import de.hbt.cfa.entity.SomeContext;
import de.hbt.cfa.entity.SomeValueObject;

public class T1_Immutability {

    public static void main(String[] args) {
        // this is a record, which is immutable
        final SomeContext context = SomeContext.builder().longVal(42L).stringVal("something").build();

        System.out.println(context.longVal()); // has only getters no setters
        System.out.println(context.stringVal());
        context.toBuilder().build(); // creates a copy

        // this is a lombok.@Value object, which is also immutable
        SomeValueObject someValueObject = new SomeValueObject("lombok", "is...");
        System.out.println(someValueObject.getFirstStringVal()); // also has only getters no setters
        System.out.println(someValueObject.getSecondStringVal());
    }
}
