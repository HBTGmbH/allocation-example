package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.TimeSlot;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends ListCrudRepository<TimeSlot, Long> {
}
