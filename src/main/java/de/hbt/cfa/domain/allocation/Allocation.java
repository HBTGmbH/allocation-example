package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Allocation {

    @Singular
    List<Participant> unassignedParticipants;

    @Singular
    List<Activity> activities;

}
