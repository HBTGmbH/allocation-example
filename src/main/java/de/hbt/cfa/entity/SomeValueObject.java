package de.hbt.cfa.entity;

import lombok.Value;

@Value
public class SomeValueObject {

    // alle fields are automatically private through @Value definition
    String firstStringVal;
    String secondStringVal;
}
