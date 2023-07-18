package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AllocationService {

    private final ActivityRepository activityRepository;

    private final AllocationMapper allocationMapper;

    public AllocationDTO getAllocation(Long activityId) {
        return activityRepository.findById(activityId)
                .map(this::buildAllocation)
                .orElse(null);
    }

    public AllocationDTO updateParticipants(Long activityId, List<TimeSlotDTO> timeSlots) {
        return activityRepository.findById(activityId)
                .map(activity -> updateActivityTimeSlots(activity, timeSlots))
                .map(activityRepository::save)
                .map(this::buildAllocation)
                .orElse(null);
    }

    private Activity updateActivityTimeSlots(Activity activity, List<TimeSlotDTO> timeSlots) {
        activity.getTimeSlots().forEach(existingTimeSlot ->
                timeSlots.stream()
                        .filter(ts -> ts.id().equals(existingTimeSlot.getId()))
                        .findFirst()
                        .map(TimeSlotDTO::participants)
                        //TODO: check that participants exist and are not assigned to multiple time slots
                        .map(allocationMapper::toParticipants)
                        .ifPresent(existingTimeSlot::setParticipants));
        return activity;
    }

    private AllocationDTO buildAllocation(Activity activity) {
        return AllocationDTO.builder()
                .unassignedParticipants(getAllUnassignedParticipantsForActivity(activity.getId()))
                .activity(allocationMapper.toActivityDTO(activity))
                .build();
    }

    private List<ParticipantDTO> getAllUnassignedParticipantsForActivity(Long activityId) {
        List<Participant> unassignedParticipants = activityRepository.findAllUnassignedParticipantsForActivity(activityId);
        return allocationMapper.toParticipantDTOs(unassignedParticipants);
    }
}
