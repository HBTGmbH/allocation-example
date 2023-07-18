package de.hbt.cfa.entity;

import java.util.Arrays;
import java.util.List;

public class EntityFixtures {

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
}
