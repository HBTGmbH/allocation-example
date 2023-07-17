package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import de.hbt.cfa.entity.TimeSlot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ActivityRepositoryTest {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    ActivityRepository activityRepository;

    @AfterEach
    public void cleanUp() {
        participantRepository.deleteAll();
        activityRepository.deleteAll();
    }

    @Test
    public void shouldReturnUnassignedParticipants() {
        // given
        var sku = participantRepository.save(Participant.builder().name("sku").build());
        var lri = participantRepository.save(Participant.builder().name("lri").build());
        var jsc = participantRepository.save(Participant.builder().name("jsc").build());
        var slot1 = timeSlotWithParticipant("Slot 1", lri);
        var slot2 = timeSlotWithParticipant("Slot 2", jsc);
        var id = activityWithTimeSlots(slot1, slot2).getId();

        // when
        List<Participant> participants = activityRepository.findAllUnassignedParticipantsForActivity(id);

        // then
        assertThat(participants).containsOnly(sku);
    }

    private TimeSlot timeSlotWithParticipant(String name, Participant participant) {
        return TimeSlot.builder().name(name).participant(participant).build();
    }

    private Activity activityWithTimeSlots(TimeSlot... timeSlots) {
        var activity = Activity.builder().name("Activity 1")
                .timeSlots(List.of(timeSlots))
                .build();
        Arrays.stream(timeSlots).forEach(slot -> slot.setActivity(activity));
        Activity saved = activityRepository.save(activity);
        return saved;
    }
}