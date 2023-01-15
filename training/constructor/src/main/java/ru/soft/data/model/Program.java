package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.WorkoutsSchema;
import ru.soft.data.BaseEntity;

import java.util.UUID;

@Getter
@Table(name = "program")
@ToString(callSuper = true)
@JsonIncludeProperties({"id", "title", "description", "workoutsSchema"})
public class Program extends BaseEntity {

    @Column("title")
    private final String title;

    @Column("description")
    private final String description;

    @NotNull
    @Column("workouts_schema")
    private final WorkoutsSchema workoutsSchema;

    @PersistenceCreator
    public Program(UUID id, String title, String description, WorkoutsSchema workoutsSchema) {
        super(id, false);
        this.title = title;
        this.description = description;
        this.workoutsSchema = workoutsSchema;
    }

    @Builder
    public Program(UUID id, boolean isNew, String title, String description, WorkoutsSchema workoutsSchema) {
        super(id, isNew);
        this.title = title;
        this.description = description;
        this.workoutsSchema = workoutsSchema;
    }

    @Override
    protected Program withId(UUID id, boolean isNew) {
        return Program.builder()
                .id(id)
                .isNew(isNew)
                .title(this.title())
                .description(this.description())
                .workoutsSchema(this.workoutsSchema())
                .build();
    }
}
