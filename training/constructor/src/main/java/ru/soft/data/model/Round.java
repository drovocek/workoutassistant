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
import ru.soft.common.data.StationsSchema;
import ru.soft.data.BaseEntity;

import java.util.UUID;

@Getter
@Table(name = "round")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "title", "description", "stationsSchema"})
public class Round extends BaseEntity {

    @NotNull
    @Column("stations_schema")
    private final StationsSchema stationsSchema;

    @NotBlank
    @Column("title")
    private final String title;

    @Column("description")
    private final String description;

    @Builder
    public Round(UUID id, boolean isNew, StationsSchema stationsSchema, String title, String description) {
        super(id, isNew);
        this.stationsSchema = stationsSchema;
        this.title = title;
        this.description = description;
    }

    @PersistenceCreator
    public Round(UUID id, StationsSchema stationsSchema, String title, String description) {
        super(id, false);
        this.stationsSchema = stationsSchema;
        this.title = title;
        this.description = description;
    }

    @Override
    protected Round withId(UUID id, boolean isNew) {
        return Round.builder()
                .id(id)
                .isNew(isNew)
                .stationsSchema(this.stationsSchema())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
