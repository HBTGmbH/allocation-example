package de.hbt.cfa.entity;

import java.util.Arrays;
import java.util.List;

public class EntityFixtures {

    public static Participant participant(String name) {
        var participant = new Participant();
        participant.setName(name);
        return participant;
    }

    public static TimeSlot timeSlotWithParticipant(String timeSlotName, Participant participant) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setName(timeSlotName);
        timeSlot.setParticipants(List.of(participant));
        return timeSlot;
    }

    public static Activity activityWithTimeSlots(TimeSlot... timeSlots) {
        var activity = new Activity();
        activity.setId(1L);
        activity.setName("Activity 1");
        activity.setTimeSlots(List.of(timeSlots));

        Arrays.stream(timeSlots).forEach(slot -> slot.setActivity(activity));
        return activity;
    }
}
