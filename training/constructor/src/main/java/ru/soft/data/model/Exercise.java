package ru.soft.data.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "exercise")
public class Exercise extends BaseEntity {

    @NotBlank
    @Column("title")
    private final String title;

    @NotBlank
    @Column("description")
    private final String description;

    @Min(value = 1)
    @Max(value = 10)
    @Column("complexity")
    private final int complexity;

    @Builder
    public Exercise(UUID id, boolean isNew, String title, String description, int complexity) {
        super(id, isNew);
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @PersistenceCreator
    public Exercise(UUID id, String title, String description, int complexity) {
        super(id, false);
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return Exercise.builder()
                .id(id)
                .isNew(true)
                .title(this.title())
                .description(this.description())
                .complexity(this.complexity())
                .build();
    }
}
