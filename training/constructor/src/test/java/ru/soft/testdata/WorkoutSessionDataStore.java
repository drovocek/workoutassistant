package ru.soft.testdata;

import org.springframework.stereotype.Component;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.WorkoutSession;
import ru.soft.web.mapper.TOMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutSessionDataStore implements TestDataStore<WorkoutSession, WorkoutSessionTo> {

    private final TOMapper<WorkoutSession, WorkoutSessionTo> mapper;

    public WorkoutSessionDataStore(TOMapper<WorkoutSession, WorkoutSessionTo> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<WorkoutSession> entities() {
        return List.of(
                new WorkoutSession(UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                        createWorkoutPlanSnapshot(),
                        LocalDateTime.of(2016,6,22,19,10,25)
                        ,"Easy training"),
                new WorkoutSession(UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        createWorkoutPlanSnapshot(),
                        LocalDateTime.of(2016,6,22,19,10,25),
                        "Medium training"),
                new WorkoutSession(UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        createWorkoutPlanSnapshot(),
                        LocalDateTime.of(2016,6,22,19,10,25),
                        "Hard training")
        );
    }

    @Override
    public List<WorkoutSessionTo> tos() {
        return entities().stream()
                .map(this.mapper::toTo)
                .toList();
    }

    public static WorkoutPlanSnapshot createWorkoutPlanSnapshot() {
        return WorkoutPlanSnapshot.builder()
                .workoutSchemaSnapshot(WorkoutPlanTestDataStore.createWorkoutSchemaSnapshot())
                .title("workout title")
                .description("workout description")
                .build();
    }
}
