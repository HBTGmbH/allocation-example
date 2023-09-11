package de.hbt.cfa.domain.allocation;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ParticipantDTO(
        Long id,
        String name) {
}
