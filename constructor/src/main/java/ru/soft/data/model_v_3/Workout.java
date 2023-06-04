package ru.soft.data.model_v_3;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.load.ConsistencyViolationException;
import ru.soft.data.load.Station;

import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "workout")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "userId", "name", "note", "stations"})
public class Workout extends BaseUserEntity {

    private final List<Station> stations;

    @Builder
    public Workout(UUID userId, UUID id, String name, String note, List<Station> stations, boolean isNew) {
        super(userId, id, name, note, isNew);
        this.stations = stations;
        if (this.stations.isEmpty()) {
            throw new ConsistencyViolationException("stations can't be empty");
        }
    }

    @PersistenceCreator
    public Workout(UUID userId, UUID id, String name, String note, List<Station> stations) {
        this(userId, id, name, note, stations, false);
    }

    @Override
    protected Workout withId(UUID id, boolean isNew) {
        return Workout.builder()
                .id(id)
                .isNew(isNew)
                .name(this.getName())
                .note(this.getNote())
                .stations(this.getStations())
                .build();
    }
}
