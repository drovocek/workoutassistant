package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.RoundsSchema;
import ru.soft.data.BaseEntity;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout")
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "title", "description", "roundsSchema"})
public class Workout extends BaseEntity {

    @NotNull
    @Column("rounds_schema")
    protected final RoundsSchema roundsSchema;

    @NotBlank
    @Column("title")
    protected final String title;

    @Column("description")
    protected final String description;

    @Builder
    public Workout(UUID id, boolean isNew, RoundsSchema roundsSchema, String title, String description) {
        super(id, isNew);
        this.roundsSchema = roundsSchema;
        this.title = title;
        this.description = description;
    }

    @PersistenceCreator
    public Workout(UUID id, RoundsSchema roundsSchema, String title, String description) {
        super(id, false);
        this.roundsSchema = roundsSchema;
        this.title = title;
        this.description = description;
    }

    @Override
    protected Workout withId(UUID id, boolean isNew) {
        return Workout.builder()
                .id(id)
                .isNew(isNew)
                .roundsSchema(this.roundsSchema())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
