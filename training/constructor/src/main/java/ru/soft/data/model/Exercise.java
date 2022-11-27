package ru.soft.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
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
    private final Integer complexity;

    @Builder
    public Exercise(UUID id, boolean isNew, String title, String description, Integer complexity) {
        super(id, isNew);
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @PersistenceCreator
    public Exercise(UUID id, String title, String description, Integer complexity) {
        super(id, false);
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return new Exercise(id, true, this.getTitle(), this.getDescription(), this.getComplexity());
    }
}
