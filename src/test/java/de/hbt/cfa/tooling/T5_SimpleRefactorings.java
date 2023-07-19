package de.hbt.cfa.tooling;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class T5_SimpleRefactorings {

    @Test
    void extractLocalVariableParameterOrConstant() {
        var a = unneededIndirection(); //place the caret on the method name and press Ctrl-Alt-N to inline the method
        var b = 42; //select 42 and press Ctrl-Alt-C to extract a constant with the value 42
        var c = 24; //select c and press Ctrl-Alt-C to convert c to a constant
        System.out.println(a + b + c); //select a + b + c and press Ctrl-Alt-V to extract a local variable
    }

    @Test
    void extractMethod() {
        //select the expression on the right side of = and press Ctrl-Alt-M to extract a method
        var stream123 = Arrays.stream(new int[]{1, 2, 3});
        var stream456 = Arrays.stream(new int[]{4, 5, 6}); //IntelliJ should suggest to replace this occurrence as well

        System.out.println(stream123.sum() + stream456.sum());
    }

    private static int unneededIndirection() {
        // place the caret on the method name and press Shift-F6 to rename the method
        return getSquarrrre();
    }

    private static int getSquarrrre() {
        return 2 * 2; //place the caret at one of the twos in the expression 2 * 2
        // and press Ctrl-Alt-P to convert 2 to a parameter
    }
}
