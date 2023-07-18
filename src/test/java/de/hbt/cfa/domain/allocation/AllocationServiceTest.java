package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.EntityFixtures;
import de.hbt.cfa.entity.Participant;
import de.hbt.cfa.entity.TimeSlot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
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

    private AllocationMapper allocationMapper = Mappers.getMapper(AllocationMapper.class);

    @Test
    public void shouldReturnCorrectAllocation() {
        //given
        var allocationService = new AllocationService(activityRepository, allocationMapper);

        var activity = activityWithTimeSlots(timeSlot1WithParticipant(participant("lri")));

        var expectedAllocation = allocationDTO(
                singleParticipantDTO("sku"),
                activityWithTimeSlotsDTO(singleTimeSlotDTOWithParticipant("lri")));

        when(activityRepository.findById(activity.getId())).thenReturn(Optional.of(activity));
        when(activityRepository.findAllUnassignedParticipantsForActivity(activity.getId())).thenReturn(List.of(participant("sku")));

        //when
        var result = allocationService.getAllocation(activity.getId());

        //then
        assertThat(result).isEqualTo(expectedAllocation);
    }

}
