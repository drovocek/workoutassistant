package ru.soft.data.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_session")
public class WorkoutSession extends BaseEntity {

    @NotNull
    @MappedCollection(idColumn = "workout_plan_id")
    private final Workout plan;

    @MappedCollection(idColumn = "workout_fact_id")
    private final Workout fact;

    @NotNull
    @Column("dateTime")
    private final LocalDateTime dateTime;

    @Column("note")
    private final String note;

    @PersistenceCreator
    public WorkoutSession(UUID id, Workout plan, Workout fact, LocalDateTime dateTime, String note) {
        super(id, false);
        this.plan = plan;
        this.fact = fact;
        this.dateTime = dateTime;
        this.note = note;
    }

    @Builder
    public WorkoutSession(UUID id, boolean isNew, Workout plan, Workout fact, LocalDateTime dateTime, String note) {
        super(id, isNew);
        this.plan = plan;
        this.fact = fact;
        this.dateTime = dateTime;
        this.note = note;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutSession.builder()
                .id(id)
                .isNew(true)
                .plan(this.plan())
                .fact(this.fact())
                .dateTime(this.dateTime())
                .note(this.note())
                .build();
    }
}
