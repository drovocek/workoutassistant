package ru.soft.testdata;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutResultTo;
import ru.soft.data.model.WorkoutResult;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.mapper.WorkoutResultTOMapper;

import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutResultTestDataStore implements TestDataStore<WorkoutResult, WorkoutResultTo> {

    private final static WorkoutResultTestDataStore INSTANCE = new WorkoutResultTestDataStore();

    public static WorkoutResultTestDataStore getInstance() {
        return INSTANCE;
    }

    private final static WorkoutResult ENTITY =
            new WorkoutResult(UUID.fromString("6ab39d60-7a52-11ed-a1eb-0242ac120002"),
                    UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                    WorkoutPlanTestDataStore.workoutSchemaSnapshot()
                    , "Push-up training", "Push-up description");

    private final TOMapper<WorkoutResult, WorkoutResultTo> toMapper;

    private WorkoutResultTestDataStore() {
        this.toMapper = new WorkoutResultTOMapper();
    }

    @Override
    public WorkoutResult entity() {
        return ENTITY;
    }

    @Override
    public List<WorkoutResult> entities() {
        return List.of(
                entity(),
                new WorkoutResult(UUID.fromString("6f8f8c22-7a52-11ed-a1eb-0242ac120002"),
                        UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"),
                        WorkoutPlanTestDataStore.workoutSchemaSnapshot()
                        , "Barbell squat title", "Barbell squat description")
        );
    }

    @Override
    public WorkoutResultTo to() {
        return this.toMapper.toTo(ENTITY);
    }

    @Override
    public List<WorkoutResultTo> tos() {
        return entities().stream()
                .map(this.toMapper::toTo)
                .toList();
    }
}
