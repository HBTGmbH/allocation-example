package de.hbt.cfa.tooling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.fail;

public class T1_ContextActions {

    class ImportDependencyAndImplementInterface //implements ParticipantRepository
    {
    }

    @ParameterizedTest
    @CsvSource({"true, false"})
    public void invertIfCondition(boolean condition1, boolean condition2) {
        if (!condition1 || condition2) {
            fail();
        } else {
            System.out.println("Condition 1 is true and condition 2 is false.");
        }
    }

    @Test
    public void addStaticImport() {
        //assertThat(1).isEqualTo(1);
    }
}
