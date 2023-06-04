package ru.soft.data.model_v_3;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Table(name = "workout_plan")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "userId", "name", "note", "dateTime", "workout"})
public class WorkoutPlan extends BaseUserEntity {

    private final LocalDateTime dateTime;
    private final Workout workout;

    @Builder
    public WorkoutPlan(UUID userId, UUID id, String name, String note, boolean isNew, LocalDateTime dateTime, Workout workout) {
        super(userId, id, name, note, isNew);
        this.dateTime = dateTime;
        this.workout = workout;
    }

    @PersistenceCreator
    public WorkoutPlan(UUID userId, UUID id, String name, String note, LocalDateTime dateTime, Workout workout) {
        this(userId, id, name, note, false, dateTime, workout);
    }

    @Override
    protected WorkoutPlan withId(UUID id, boolean isNew) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(isNew)
                .name(this.getName())
                .note(this.getNote())
                .dateTime(this.getDateTime())
                .workout(this.getWorkout())
                .build();
    }
}
