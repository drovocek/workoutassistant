package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
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
@Table(name = "workout")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "workoutSchema", "dateTime", "title", "note"})
public class Workout extends BaseEntity {

    @NotNull
    @JsonProperty("workoutSchema")
    private final WorkoutSchema workoutSchema;

    @Nonnull
    @NotNull
    @JsonProperty("dateTime")
    private final LocalDateTime dateTime;

    @PersistenceCreator
    public Workout(UUID id, WorkoutSchema workoutSchema, LocalDateTime dateTime, String title, String note) {
        super(id, false, title, note);
        this.workoutSchema = workoutSchema;
        this.dateTime = dateTime;
    }

    @Builder
    public Workout(UUID id, boolean isNew, WorkoutSchema workoutSchema, LocalDateTime dateTime, String title, String note) {
        super(id, isNew, title, note);
        this.workoutSchema = workoutSchema;
        this.dateTime = dateTime;
    }

    @Override
    protected Workout withId(UUID id, boolean isNew) {
        return Workout.builder()
                .id(id)
                .isNew(isNew)
                .workoutSchema(this.workoutSchema())
                .dateTime(this.dateTime())
                .title(this.title())
                .note(this.note())
                .build();
    }
}
