package ru.soft.testdata;

import org.springframework.stereotype.Component;
import ru.soft.common.data.snapshot.WorkoutRoundSnapshot;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.web.mapper.TOMapper;

import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutPlanTestDataStore implements TestDataStore<WorkoutPlan, WorkoutPlanTo> {

    private final TOMapper<WorkoutPlan, WorkoutPlanTo> mapper;

    public WorkoutPlanTestDataStore(TOMapper<WorkoutPlan, WorkoutPlanTo> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<WorkoutPlan> entities() {
        return List.of(
                new WorkoutPlan(UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        createWorkoutSchemaSnapshot()
                        , "Push-up training", "Push-up description"),
                new WorkoutPlan(UUID.fromString("612ccb86-7a52-11ed-a1eb-0242ac120002"),
                        createWorkoutSchemaSnapshot()
                        , "Barbell squats training", "Barbell squats description"),
                new WorkoutPlan(UUID.fromString("65d1de6a-7a52-11ed-a1eb-0242ac120002"),
                        createWorkoutSchemaSnapshot()
                        , "Pull-ups training", "Pull-ups description")
        );
    }

    @Override
    public List<WorkoutPlanTo> tos() {
        return entities().stream()
                .map(this.mapper::toTo)
                .toList();
    }

    public static WorkoutSchemaSnapshot createWorkoutSchemaSnapshot() {
        return new WorkoutSchemaSnapshot(List.of(
                WorkoutRoundSnapshot.builder()
                        .workoutRoundSchemaSnapshot(WorkoutRoundTestDataStore.createWorkoutRoundSchemaSnapshot())
                        .title("round title_1")
                        .description("round description_1")
                        .build(),
                WorkoutRoundSnapshot.builder()
                        .workoutRoundSchemaSnapshot(WorkoutRoundTestDataStore.createWorkoutRoundSchemaSnapshot())
                        .title("round title_2")
                        .description("round description_2")
                        .build(),
                WorkoutRoundSnapshot.builder()
                        .workoutRoundSchemaSnapshot(WorkoutRoundTestDataStore.createWorkoutRoundSchemaSnapshot())
                        .title("round title_3")
                        .description("round description_3")
                        .build()
        ));
    }
}
