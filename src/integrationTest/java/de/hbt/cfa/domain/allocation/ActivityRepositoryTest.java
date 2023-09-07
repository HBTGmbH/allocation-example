package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.EntityFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        var sku = participantRepository.save(EntityFixtures.participant("sku"));
        var lri = participantRepository.save(EntityFixtures.participant("lri"));
        var jsc = participantRepository.save(EntityFixtures.participant("jsc"));
        var slot1 = EntityFixtures.timeSlotWithParticipant("Slot 1", lri);
        var slot2 = EntityFixtures.timeSlotWithParticipant("Slot 2", jsc);
        var id = activityRepository.save(EntityFixtures.activityWithTimeSlots(slot1, slot2)).getId();

        // when
        var participants = activityRepository.findAllUnassignedParticipantsForActivity(id);

        // then
        assertThat(participants).containsOnly(sku);
    }
}