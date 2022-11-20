package ru.soft.data.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@ToString
@Table(name = "EXERCISE")
public class Exercise extends BaseEntity {

    @Column("TITLE")
    private final String title;

    @Column("DESCRIPTION")
    private final String description;

    @Column("COMPLEXITY")
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
}
