package ru.soft.data.model;

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

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "workout_round")
public class WorkoutRound extends BaseEntity {

    @Column("round_schema")
    private final WorkoutRoundSchemaSnapshot schema;

    @Column("title")
    private final String title;

    @Column("description")
    private final String description;

    @Min(value = 1)
    @Max(value = 10)
    @Column("complexity")
    private final int complexity;

    @Builder
    public WorkoutRound(UUID id, boolean isNew, WorkoutRoundSchemaSnapshot schema, String title, String description, int complexity) {
        super(id, isNew);
        this.schema = schema;
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @PersistenceCreator
    public WorkoutRound(UUID id, WorkoutRoundSchemaSnapshot schema, String title, String description, int complexity) {
        super(id, false);
        this.schema = schema;
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutRound.builder()
                .id(id)
                .isNew(true)
                .schema(this.schema())
                .title(this.title())
                .description(this.description())
                .complexity(this.complexity())
                .build();
    }
}
