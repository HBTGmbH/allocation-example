package de.hbt.cfa.entity;

@FunctionalInterface
public interface CurryingString<O> {

    O stringVal(String stringVal);
}
