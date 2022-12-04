package ru.soft.data.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_station")
public class WorkoutStation extends BaseEntity {

    @NotNull
    @MappedCollection(idColumn = "exercise_id")
    private final Exercise exercise;

    @Min(value = 0)
    @Max(value = 1_000)
    @Column("repetitions")
    private final int repetitions;

    @Min(value = 0)
    @Max(value = 500)
    @Column("weight")
    private final int weight;

    @Min(value = 0)
    @Max(value = 100_000)
    @Column("duration")
    private final int duration;

    @Min(value = 0)
    @Max(value = 60)
    @Column("rest")
    private final int rest;

    @Min(value = 0)
    @Max(value = 100)
    @Column("order")
    private final int order;

    @Builder
    public WorkoutStation(UUID id, boolean isNew, Exercise exercise, int repetitions, int weight, int duration, int rest, int order) {
        super(id, isNew);
        this.exercise = exercise;
        this.repetitions = repetitions;
        this.weight = weight;
        this.duration = duration;
        this.rest = rest;
        this.order = order;
    }

    @PersistenceCreator
    public WorkoutStation(UUID id, Exercise exercise, int repetitions, int weight, int duration, int rest, int order) {
        super(id, false);
        this.exercise = exercise;
        this.repetitions = repetitions;
        this.weight = weight;
        this.duration = duration;
        this.rest = rest;
        this.order = order;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutStation.builder()
                .id(id)
                .isNew(true)
                .exercise(this.exercise())
                .repetitions(this.repetitions())
                .weight(this.weight())
                .duration(this.duration())
                .rest(this.rest())
                .order(this.order())
                .build();
    }
}
