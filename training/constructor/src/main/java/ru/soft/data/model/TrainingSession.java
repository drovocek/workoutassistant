package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.elements.WorkoutSchema;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Table(name = "training_session")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "workoutSchema", "dateTime", "title", "note"})
public class TrainingSession extends BaseEntity {

    @NotNull
    @JsonProperty("workoutSchema")
    private final WorkoutSchema workoutSchema;

    @JsonProperty("dateTime")
    private final LocalDateTime dateTime;

    @PersistenceCreator
    public TrainingSession(UUID id, WorkoutSchema workoutSchema, LocalDateTime dateTime, String title, String note) {
        super(id, false, title, note);
        this.workoutSchema = workoutSchema;
        this.dateTime = dateTime;
    }

    @Builder
    public TrainingSession(UUID id, boolean isNew, WorkoutSchema workoutSchema, LocalDateTime dateTime, String title, String note) {
        super(id, isNew, title, note);
        this.workoutSchema = workoutSchema;
        this.dateTime = dateTime;
    }

    @Override
    protected TrainingSession withId(UUID id, boolean isNew) {
        return TrainingSession.builder()
                .id(id)
                .isNew(isNew)
                .workoutSchema(this.workoutSchema())
                .dateTime(this.dateTime())
                .title(this.title())
                .note(this.note())
                .build();
    }
}
