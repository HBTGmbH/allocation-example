package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Participant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static de.hbt.cfa.domain.allocation.AllocationFixtures.*;
import static de.hbt.cfa.entity.EntityFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AllocationServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Test
    public void shouldReturnCorrectAllocation() {
        //given
        var allocationService = new AllocationService(activityRepository, AllocationMapper.INSTANCE);

        Participant participant = participant("lri");
        var activity = activityWithTimeSlots(timeSlotWithParticipant("Slot 1", participant));

        var expectedAllocation = allocationDTO(
                singleParticipantDTO("sku"),
                activityWithTimeSlotsDTO(singleTimeSlotDTOWithParticipant("Slot 1", "lri")));

        when(activityRepository.findById(activity.getId())).thenReturn(Optional.of(activity));
        when(activityRepository.findAllUnassignedParticipantsForActivity(activity.getId())).thenReturn(List.of(participant("sku")));

        //when
        var result = allocationService.getAllocation(activity.getId());

        //then
        assertThat(result).isEqualTo(expectedAllocation);
    }

}
