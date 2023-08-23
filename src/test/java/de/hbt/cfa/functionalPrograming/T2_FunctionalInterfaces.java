package de.hbt.cfa.functionalPrograming;

import de.hbt.cfa.entity.SomeValueObject;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class T2_FunctionalInterfaces {

    public static void main(String[] args) {
        // a function has one input parameter and produces one output
        Function<Long, String> function = String::valueOf; // or verbose (aLong) -> String.valueOf(aLong);
        System.out.println("function " + function.apply(42L));

        // a supplier has no parameters and produces one output
        Supplier<String> supplier = () -> "test";
        System.out.println("supplier " + supplier.get());

        // a consumer has one input parameter and produces no output
        Consumer<Long> consumer = System.out::println; // (aLong) -> System.out.println(aLong);
        consumer.accept(23L);

        // a bi-function has two input parameters and produces one output
        BiFunction<String, String, SomeValueObject> biFunction = SomeValueObject::new; // (l1, l2) -> new SomeValueObject(l1, l2);
        final SomeValueObject someVal = biFunction.apply("testing", "bifunctions");
        System.out.println(someVal.getFirstStringVal() + " " + someVal.getSecondStringVal());

        BiConsumer<Integer, Integer> biConsumer = (l1, l2) -> System.out.println("bi-consumer " + l1*l2);
        biConsumer.accept(10, 10);
    }

}
