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
    @MappedCollection(idColumn = "workout_id")
    private final Workout plan;

    @Column("note")
    private final String note;

    @NotNull
    @Column("dateTime")
    private final LocalDateTime dateTime;

    @PersistenceCreator
    public WorkoutSession(UUID id, Workout plan, String note, LocalDateTime dateTime) {
        super(id, false);
        this.plan = plan;
        this.note = note;
        this.dateTime = dateTime;
    }

    @Builder
    public WorkoutSession(UUID id, boolean isNew, Workout plan, String note, LocalDateTime dateTime) {
        super(id, isNew);
        this.plan = plan;
        this.note = note;
        this.dateTime = dateTime;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutSession.builder()
                .id(id)
                .isNew(true)
                .plan(this.plan())
                .note(this.note())
                .dateTime(this.dateTime())
                .build();
    }
}
