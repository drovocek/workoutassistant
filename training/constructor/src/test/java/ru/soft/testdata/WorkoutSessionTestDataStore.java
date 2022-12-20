package ru.soft.testdata;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.WorkoutSession;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.mapper.WorkoutSessionTOMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutSessionTestDataStore implements TestDataStore<WorkoutSession, WorkoutSessionTo> {

    private final static WorkoutSessionTestDataStore INSTANCE = new WorkoutSessionTestDataStore();

    public static WorkoutSessionTestDataStore getInstance() {
        return INSTANCE;
    }

    private final static WorkoutSession ENTITY =
            new WorkoutSession(
                    UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                    WorkoutPlanTestDataStore.workoutPlanSnapshot(),
                    LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                    "Easy training");

    private final TOMapper<WorkoutSession, WorkoutSessionTo> toMapper;

    private WorkoutSessionTestDataStore() {
        this.toMapper = new WorkoutSessionTOMapper();
    }

    @Override
    public WorkoutSession entity() {
        return ENTITY;
    }

    @Override
    public List<WorkoutSession> entities() {
        return List.of(
                entity(),
                new WorkoutSession(UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        WorkoutPlanTestDataStore.workoutPlanSnapshot(),
                        LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                        "Medium training"),
                new WorkoutSession(UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        WorkoutPlanTestDataStore.workoutPlanSnapshot(),
                        LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                        "Hard training")
        );
    }

    @Override
    public WorkoutSessionTo to() {
        return this.toMapper.toTo(ENTITY);
    }

    @Override
    public List<WorkoutSessionTo> tos() {
        return entities().stream()
                .map(this.toMapper::toTo)
                .toList();
    }
}
