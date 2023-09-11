package de.hbt.cfa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(cascade = CascadeType.ALL)
    @Singular
    List<Participant> participants;

    @ManyToOne
    @ToString.Exclude
    Activity activity;
}
