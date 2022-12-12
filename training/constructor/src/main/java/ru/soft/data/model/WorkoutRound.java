package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

import java.util.UUID;

import static ru.soft.data.model.utils.ComplexityUtils.calculateComplexity;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "workout_round")
public class WorkoutRound extends BaseEntity {

    @JsonProperty("stations")
    @Column("round_schema")
    private final WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot;

    @Column("title")
    private final String title;

    @Column("description")
    private final String description;

    @Min(value = 1)
    @Max(value = 10)
    @Column("complexity")
    private final int complexity;

    @Builder
    public WorkoutRound(UUID id, boolean isNew, WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot, String title, String description) {
        super(id, isNew);
        this.workoutRoundSchemaSnapshot = workoutRoundSchemaSnapshot;
        this.title = title;
        this.description = description;
        this.complexity = calculateComplexity(this.workoutRoundSchemaSnapshot);
    }

    @PersistenceCreator
    public WorkoutRound(UUID id, WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot, String title, String description, int complexity) {
        super(id, false);
        this.workoutRoundSchemaSnapshot = workoutRoundSchemaSnapshot;
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutRound.builder()
                .id(id)
                .isNew(true)
                .workoutRoundSchemaSnapshot(this.workoutRoundSchemaSnapshot())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
