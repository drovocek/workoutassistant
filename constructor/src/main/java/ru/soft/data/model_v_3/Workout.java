package ru.soft.data.model_v_3;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.load.ConsistencyViolationException;

import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "workout")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "userId", "name", "note", "stationsSets"})
public class Workout extends BaseUserEntity {

    private final List<StationsSet> stationsSets;

    @Builder
    public Workout(UUID userId, UUID id, String name, String note, List<StationsSet> stationsSets, boolean isNew) {
        super(userId, id, name, note, isNew);
        this.stationsSets = stationsSets;
        if (this.stationsSets.isEmpty()) {
            throw new ConsistencyViolationException("station sets can't be empty");
        }
    }

    @PersistenceCreator
    public Workout(UUID userId, UUID id, String name, String note, List<StationsSet> stationsSets) {
        this(userId, id, name, note, stationsSets, false);
    }

    @Override
    protected Workout withId(UUID id, boolean isNew) {
        return Workout.builder()
                .id(id)
                .isNew(isNew)
                .name(this.getName())
                .note(this.getNote())
                .stationsSets(this.getStationsSets())
                .build();
    }
}
