package ru.soft.data.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.snapshot.WorkoutPlanSnapshot;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_session")
public class WorkoutSession extends BaseEntity {

    @NotNull
    @Column("workout_plan")
    private final WorkoutPlanSnapshot plan;

    @NotNull
    @Column("date_time")
    private final LocalDateTime dateTime;

    @Column("note")
    private final String note;

    @PersistenceCreator
    public WorkoutSession(UUID id, WorkoutPlanSnapshot plan, LocalDateTime dateTime, String note) {
        super(id, false);
        this.plan = plan;
        this.dateTime = dateTime;
        this.note = note;
    }

    @Builder
    public WorkoutSession(UUID id, boolean isNew, WorkoutPlanSnapshot plan, LocalDateTime dateTime, String note) {
        super(id, isNew);
        this.plan = plan;
        this.dateTime = dateTime;
        this.note = note;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutSession.builder()
                .id(id)
                .isNew(true)
                .plan(this.plan())
                .dateTime(this.dateTime())
                .note(this.note())
                .build();
    }
}
