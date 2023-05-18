package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "exercise")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "title", "note"})
public class Exercise extends BaseEntity {

    @Builder
    public Exercise(UUID id, boolean isNew, @NotBlank String title, String note) {
        super(id, isNew, title, note);
    }

    @PersistenceCreator
    public Exercise(UUID id, @NotBlank String title, String note) {
        super(id, false, title, note);
    }

    @Override
    protected Exercise withId(UUID id, boolean isNew) {
        return Exercise.builder()
                .id(id)
                .isNew(isNew)
                .title(this.title())
                .note(this.note())
                .build();
    }
}
