package de.hbt.cfa.domain.allocation;

import java.util.List;

public class AllocationFixtures {

    public static AllocationDTO allocationDTO(List<ParticipantDTO> unassignedParticipants, ActivityDTO activity) {
        return AllocationDTO.builder()
                .unassignedParticipants(unassignedParticipants)
                .activity(activity)
                .build();
    }

    public static ActivityDTO activityWithTimeSlotsDTO(List<TimeSlotDTO> timeSlots) {
        return ActivityDTO.builder().name("Activity 1").timeSlots(timeSlots).build();
    }

    public static List<TimeSlotDTO> singleTimeSlotDTOWithParticipant(String participantName) {
        return List.of(TimeSlotDTO.builder().name("Slot 1").participants(singleParticipantDTO(participantName)).build());
    }

    public static List<ParticipantDTO> singleParticipantDTO(String name) {
        return List.of(ParticipantDTO.builder().name(name).build());
    }
}
