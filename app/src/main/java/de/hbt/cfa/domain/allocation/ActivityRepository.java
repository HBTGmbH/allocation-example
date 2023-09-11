package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends ListCrudRepository<Activity, Long> {
    @Query("select p from Participant p where p not in (select ts.participants from TimeSlot ts where ts.activity.id = ?1)")
    List<Participant> findAllUnassignedParticipantsForActivity(Long activityId);
}
