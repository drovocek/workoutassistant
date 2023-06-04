package ru.soft.data.model_v_3;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.load.TrainingType;

import java.util.UUID;

@Getter
@Table(name = "exercise")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "userId", "name", "note", "trainingType"})
public class Exercise extends BaseUserEntity {

    private final TrainingType trainingType;

    @Builder
    public Exercise(UUID userId, UUID id, String name, String note, TrainingType trainingType, boolean isNew) {
        super(userId, id, name, note, isNew);
        this.trainingType = trainingType;
    }

    @PersistenceCreator
    public Exercise(UUID userId, UUID id, String name, String note, TrainingType trainingType) {
        this(userId, id, name, note, trainingType, false);
    }

    @Override
    protected Exercise withId(UUID id, boolean isNew) {
        return Exercise.builder()
                .id(id)
                .isNew(isNew)
                .name(this.getName())
                .note(this.getNote())
                .trainingType(this.getTrainingType())
                .build();
    }
}
