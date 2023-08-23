package de.hbt.cfa.entity;


import lombok.Builder;

@Builder(toBuilder = true)
public record SomeContext(String stringVal, Long longVal) {
}
