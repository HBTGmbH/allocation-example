package de.hbt.cfa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    // persisting the time slots is not necessary due to cascading
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity")
    List<TimeSlot> timeSlots;
}
