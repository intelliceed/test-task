package com.william.task.domain.event.db.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "scores")
@EqualsAndHashCode(of = "id")
@ToString(exclude = "event")
public class ScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int score1;

    @Column
    private int score2;

    @Column
    private LocalDateTime scoreTimestamp;

    @Column
    private LocalDateTime receivedTimestamp;

    @ManyToOne
    private EventEntity event;
}
