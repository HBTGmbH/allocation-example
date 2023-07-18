package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AllocationMapper {
    List<ParticipantDTO> toParticipantDTOs(List<Participant> participants);

    ActivityDTO toActivityDTO(Activity activity);

    List<Participant> toParticipants(List<ParticipantDTO> participants);
}
