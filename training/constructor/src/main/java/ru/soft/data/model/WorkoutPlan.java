package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.RoundsSchema;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_plan")
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "title", "description", "roundsSchema"})
public class WorkoutPlan extends Workout {

    @Builder
    public WorkoutPlan(UUID id, boolean isNew, RoundsSchema roundsSchema, String title, String description) {
        super(id, isNew, roundsSchema, title, description);
    }

    @PersistenceCreator
    public WorkoutPlan(UUID id, RoundsSchema roundsSchema, String title, String description) {
        super(id, roundsSchema, title, description);
    }

    @Override
    protected WorkoutPlan withId(UUID id, boolean isNew) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(isNew)
                .roundsSchema(this.roundsSchema())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
