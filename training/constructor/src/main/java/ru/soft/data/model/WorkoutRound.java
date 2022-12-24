package ru.soft.data.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.data.BaseEntity;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "workout_round")
public class WorkoutRound extends BaseEntity {

    @NotNull
    @Column("round_schema")
    private final WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot;

    @NotBlank
    @Column("title")
    private final String title;

    @Column("description")
    private final String description;

    @Builder
    public WorkoutRound(UUID id, boolean isNew, WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot, String title, String description) {
        super(id, isNew);
        this.workoutRoundSchemaSnapshot = workoutRoundSchemaSnapshot;
        this.title = title;
        this.description = description;
    }

    @PersistenceCreator
    public WorkoutRound(UUID id, WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot, String title, String description) {
        super(id, false);
        this.workoutRoundSchemaSnapshot = workoutRoundSchemaSnapshot;
        this.title = title;
        this.description = description;
    }

    @Override
    protected WorkoutRound withId(UUID id, boolean isNew) {
        return WorkoutRound.builder()
                .id(id)
                .isNew(isNew)
                .workoutRoundSchemaSnapshot(this.workoutRoundSchemaSnapshot())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
