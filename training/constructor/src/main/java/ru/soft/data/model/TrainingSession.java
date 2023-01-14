package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;
import ru.soft.data.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Table(name = "training_session")
@ToString(callSuper = true)
@JsonIncludeProperties({"id", "plan", "dateTime", "note"})
public class TrainingSession extends BaseEntity {

    @NotNull
    @Column("workout_plan")
    private final WorkoutPlanSnapshot plan;

    @NotNull
    @Column("date_time")
    private final LocalDateTime dateTime;

    @Column("note")
    private final String note;

    @PersistenceCreator
    public TrainingSession(UUID id, WorkoutPlanSnapshot plan, LocalDateTime dateTime, String note) {
        super(id, false);
        this.plan = plan;
        this.dateTime = dateTime;
        this.note = note;
    }

    @Builder
    public TrainingSession(UUID id, boolean isNew, WorkoutPlanSnapshot plan, LocalDateTime dateTime, String note) {
        super(id, isNew);
        this.plan = plan;
        this.dateTime = dateTime;
        this.note = note;
    }

    @Override
    protected TrainingSession withId(UUID id, boolean isNew) {
        return TrainingSession.builder()
                .id(id)
                .isNew(isNew)
                .plan(this.plan())
                .dateTime(this.dateTime())
                .note(this.note())
                .build();
    }
}
