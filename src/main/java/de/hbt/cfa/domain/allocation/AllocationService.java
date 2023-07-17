package de.hbt.cfa.domain.allocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AllocationService {

    private final ActivityRepository activityRepository;

    public Allocation getAllocation() {
        return Allocation.builder()
                .unassignedParticipants(activityRepository.findAllUnassignedParticipantsForActivity(0L))
                .activity(activityRepository.findById(0L).orElseThrow())
                .build();
    }

}
