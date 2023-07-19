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
        return ActivityDTO.builder()
                .id(1L)
                .name("Activity 1")
                .timeSlots(timeSlots).build();
    }

    public static List<TimeSlotDTO> singleTimeSlotDTOWithParticipant(String timeSlotName, String participantName) {
        return List.of(TimeSlotDTO.builder().name(timeSlotName).participants(singleParticipantDTO(participantName)).build());
    }

    public static List<ParticipantDTO> singleParticipantDTO(String name) {
        return List.of(ParticipantDTO.builder().name(name).build());
    }
}
