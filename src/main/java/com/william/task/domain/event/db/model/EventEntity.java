package com.william.task.domain.event.db.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "events")
@EqualsAndHashCode(of = "id")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String team1;

    @Column
    private String team2;

    @Column
    private int score1;

    @Column
    private int score2;

    @Version
    private LocalDateTime lastUpdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
    private List<ScoreEntity> scoreHistory = new ArrayList<>();
}
