package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.TimeSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AllocationService {

    private final ActivityRepository activityRepository;

    public Allocation getAllocation() {
        return Allocation.builder()
                .unassignedParticipants(activityRepository.findAllUnassignedParticipantsForActivity(1L))
                .activity(activityRepository.findById(1L).orElseThrow())
                .build();
    }

    public Allocation saveAllocation(Allocation allocation) {
        Activity newActivity = allocation.getActivities().get(0);
        Activity activity = activityRepository.findById(newActivity.getId())
                .map(updatedActivity -> {
                    updatedActivity.getTimeSlots().forEach(timeSlot -> {
                        newActivity.getTimeSlots().stream()
                                .filter(ts -> ts.getId().equals(timeSlot.getId()))
                                .map(TimeSlot::getParticipants)
                                .forEach(timeSlot::setParticipants);
                    });
                    return updatedActivity;
                }).orElseThrow();
        return Allocation.builder()
                .unassignedParticipants(activityRepository.findAllUnassignedParticipantsForActivity(activity.getId()))
                .activity(activityRepository.save(activity))
                .build();
    }
}
