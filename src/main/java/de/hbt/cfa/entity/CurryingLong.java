package de.hbt.cfa.entity;

@FunctionalInterface
public interface CurryingLong<O> {

    O longVal(Long input);
}
