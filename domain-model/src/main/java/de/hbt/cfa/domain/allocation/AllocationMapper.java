package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AllocationMapper {
    AllocationMapper INSTANCE = Mappers.getMapper(AllocationMapper.class);

    List<ParticipantDTO> toParticipantDTOs(List<Participant> participants);

    ActivityDTO toActivityDTO(Activity activity);

    List<Participant> toParticipants(List<ParticipantDTO> participants);
}
