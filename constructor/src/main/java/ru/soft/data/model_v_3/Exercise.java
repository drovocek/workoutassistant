package ru.soft.data.model_v_3;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.load.LoadType;

import java.util.UUID;

@Getter
@Table(name = "exercise")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "userId", "name", "note", "loadType"})
public class Exercise extends BaseUserEntity {

    private final LoadType loadType;

    @Builder
    public Exercise(UUID userId, UUID id, String name, String note, LoadType loadType, boolean isNew) {
        super(userId, id, name, note, isNew);
        this.loadType = loadType;
    }

    @PersistenceCreator
    public Exercise(UUID userId, UUID id, String name, String note, LoadType loadType) {
        this(userId, id, name, note, loadType, false);
    }

    @Override
    protected Exercise withId(UUID id, boolean isNew) {
        return Exercise.builder()
                .id(id)
                .isNew(isNew)
                .name(this.getName())
                .note(this.getNote())
                .loadType(this.getLoadType())
                .build();
    }
}
