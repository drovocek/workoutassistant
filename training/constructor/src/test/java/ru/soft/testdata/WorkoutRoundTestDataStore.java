package ru.soft.testdata;

import org.springframework.stereotype.Component;
import ru.soft.common.data.snapshot.*;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.data.model.WorkoutRound;
import ru.soft.web.mapper.TOMapper;

import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutRoundTestDataStore implements TestDataStore<WorkoutRound, WorkoutRoundTo> {

    private final TOMapper<WorkoutRound, WorkoutRoundTo> mapper;

    public WorkoutRoundTestDataStore(TOMapper<WorkoutRound, WorkoutRoundTo> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<WorkoutRound> entities() {
        return List.of(
                new WorkoutRound(UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                        createWorkoutRoundSchemaSnapshot()
                        ,"Warm up", "Warm up"),
                new WorkoutRound(UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                        createWorkoutRoundSchemaSnapshot()
                        ,"Strength", "For strength"),
                new WorkoutRound(UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                        createWorkoutRoundSchemaSnapshot()
                        ,"Endurance", "For endurance")
        );
    }

    @Override
    public List<WorkoutRoundTo> tos() {
        return entities().stream()
                .map(this.mapper::toTo)
                .toList();
    }

    public static WorkoutRoundSchemaSnapshot createWorkoutRoundSchemaSnapshot() {
        return new WorkoutRoundSchemaSnapshot(List.of(
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("station title_1", "station description_1", 1),
                        3, 0, 100, 20, 1),
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("station title_2", "station description_2", 2),
                        6, 10, 0, 10, 2
                ),
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("station title_3", "station description_3", 3),
                        1, 5, 10, 100, 0
                )
        ));
    }
}
