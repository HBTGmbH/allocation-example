package de.hbt.cfa.domain.allocation;

import de.hbt.cfa.entity.Participant;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends ListCrudRepository<Participant, Long> {


}
