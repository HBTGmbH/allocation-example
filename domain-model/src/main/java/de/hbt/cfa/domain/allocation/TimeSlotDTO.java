package de.hbt.cfa.domain.allocation;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
public record TimeSlotDTO(
        Long id,
        String name,
        List<ParticipantDTO> participants) {
}
