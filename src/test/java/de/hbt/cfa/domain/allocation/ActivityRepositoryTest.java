package de.hbt.cfa.domain.allocation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static de.hbt.cfa.domain.allocation.AllocationFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ActivityRepositoryTest {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    ActivityRepository activityRepository;

    @BeforeEach
    public void cleanUp() {
        participantRepository.deleteAll();
        activityRepository.deleteAll();
    }

    @Test
    public void shouldReturnUnassignedParticipants() {
        // given
        var sku = participantRepository.save(participant("sku"));
        var lri = participantRepository.save(participant("lri"));
        var jsc = participantRepository.save(participant("jsc"));
        var slot1 = timeSlot1WithParticipant(lri);
        var slot2 = timeSlot2WithParticipant(jsc);
        var id = activityRepository.save(activityWithTimeSlots(slot1, slot2)).getId();

        // when
        var participants = activityRepository.findAllUnassignedParticipantsForActivity(id);

        // then
        assertThat(participants).containsOnly(sku);
    }
}