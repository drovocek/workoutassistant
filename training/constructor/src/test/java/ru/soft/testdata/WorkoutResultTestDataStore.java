package ru.soft.testdata;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutResultTo;
import ru.soft.data.model.WorkoutResult;
import ru.soft.web.mapper.TOMapper;

import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutResultTestDataStore implements TestDataStore<WorkoutResult, WorkoutResultTo> {

    private final TOMapper<WorkoutResult, WorkoutResultTo> mapper;

    public WorkoutResultTestDataStore(TOMapper<WorkoutResult, WorkoutResultTo> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<WorkoutResult> entities() {
        return List.of(
                new WorkoutResult(UUID.fromString("6ab39d60-7a52-11ed-a1eb-0242ac120002"),
                        UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                        WorkoutPlanTestDataStore.createWorkoutSchemaSnapshot()
                        , "Push-up training", "Push-up description"),
                new WorkoutResult(UUID.fromString("6f8f8c22-7a52-11ed-a1eb-0242ac120002"),
                        UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"),
                        WorkoutPlanTestDataStore.createWorkoutSchemaSnapshot()
                        , "Barbell squat title", "Barbell squat description")
        );
    }

    @Override
    public List<WorkoutResultTo> tos() {
        return entities().stream()
                .map(this.mapper::toTo)
                .toList();
    }
}
