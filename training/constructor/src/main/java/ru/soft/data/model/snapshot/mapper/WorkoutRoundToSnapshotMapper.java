package ru.soft.data.model.snapshot.mapper;

import lombok.experimental.UtilityClass;
import ru.soft.data.model.WorkoutRound;
import ru.soft.data.model.snapshot.WorkoutRoundSnapshot;

import java.util.List;

@UtilityClass
public class WorkoutRoundToSnapshotMapper {

    public static List<WorkoutRoundSnapshot> toSnapshot(List<WorkoutRound> rounds) {
        return rounds.stream()
                .map(WorkoutRoundToSnapshotMapper::toSnapshot)
                .toList();
    }

    public static WorkoutRoundSnapshot toSnapshot(WorkoutRound round) {
        return new WorkoutRoundSnapshot(
                round.workoutRoundSchemaSnapshot().workoutStations(),
                round.title(),
                round.description(),
                round.complexity()
        );
    }
}
