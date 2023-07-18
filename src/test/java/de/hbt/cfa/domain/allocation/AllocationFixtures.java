package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import de.hbt.cfa.entity.TimeSlot;

import java.util.Arrays;
import java.util.List;

public class AllocationFixtures {
    public static Participant participant(String name) {
        var participant = new Participant();
        participant.setName(name);
        return participant;
    }

    public static TimeSlot timeSlot1WithParticipant(Participant participant) {
        var timeSlot = new TimeSlot();
        timeSlot.setName("Slot 1");
        timeSlot.setParticipants(List.of(participant));
        return timeSlot;
    }

    public static TimeSlot timeSlot2WithParticipant(Participant participant) {
        var timeSlot = new TimeSlot();
        timeSlot.setName("Slot 2");
        timeSlot.setParticipants(List.of(participant));
        return timeSlot;
    }

    public static Activity activityWithTimeSlots(TimeSlot... timeSlots) {
        var activity = new Activity();
        activity.setName("Activity 1");
        activity.setTimeSlots(List.of(timeSlots));

        Arrays.stream(timeSlots).forEach(slot -> slot.setActivity(activity));
        return activity;
    }

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
