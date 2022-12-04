package ru.soft.data.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;

import java.util.Set;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_round")
public class WorkoutRound extends BaseEntity {

    @MappedCollection(idColumn = "workout_station_id")
    private final Set<WorkoutStation> workoutStations;

    @Column("title")
    private final String title;

    @Column("description")
    private final String description;

    @Min(value = 0)
    @Max(value = 100_00)
    @Column("rest")
    private final int rest;

    @Min(value = 0)
    @Max(value = 100)
    @Column("order")
    private final int order;

    @PersistenceCreator
    public WorkoutRound(UUID id, Set<WorkoutStation> workoutStations, String title, String description, int rest, int order) {
        super(id, false);
        this.workoutStations = workoutStations;
        this.title = title;
        this.description = description;
        this.rest = rest;
        this.order = order;
    }

    @Builder
    public WorkoutRound(UUID id, boolean isNew, Set<WorkoutStation> workoutStations, String title, String description, int rest, int order) {
        super(id, isNew);
        this.workoutStations = workoutStations;
        this.title = title;
        this.description = description;
        this.rest = rest;
        this.order = order;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutRound.builder()
                .id(id)
                .isNew(true)
                .workoutStations(this.workoutStations())
                .title(this.title())
                .description(this.description())
                .rest(this.rest())
                .order(this.order())
                .build();
    }
}
