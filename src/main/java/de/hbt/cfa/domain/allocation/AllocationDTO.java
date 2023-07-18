package de.hbt.cfa.domain.allocation;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
public record AllocationDTO(
        List<ParticipantDTO> unassignedParticipants,
        ActivityDTO activity) {
}
