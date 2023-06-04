package ru.soft.data.model_v_3;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.HasOrder;
import ru.soft.data.load.Station;

import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "stations_set")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "userId", "name", "note", "stations"})
public class StationsSet extends BaseUserEntity implements HasOrder {

    @Transient
    private final int order;
    private final List<Station> stations;

    @Builder
    public StationsSet(UUID userId, UUID id, String name, String note, boolean isNew, int order, List<Station> stations) {
        super(userId, id, name, note, isNew);
        this.order = order;
        this.stations = stations;
    }

    @PersistenceCreator
    public StationsSet(UUID userId, UUID id, String name, String note, int order, List<Station> stations) {
        super(userId, id, name, note, false);
        this.order = order;
        this.stations = stations;
    }


    @Override
    protected StationsSet withId(UUID id, boolean isNew) {
        return StationsSet.builder()
                .id(id)
                .isNew(isNew)
                .name(this.getName())
                .note(this.getNote())
                .order(this.getOrder())
                .stations(this.getStations())
                .build();
    }
}
