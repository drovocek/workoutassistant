package ru.soft.data.repository;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.data.model.WorkoutSession;

import java.time.LocalDateTime;
import java.util.UUID;

import static ru.soft.testdata.WorkoutPlanTestDataStore.workoutPlanSnapshot;

@DataJdbcTest
class WorkoutSessionRepositoryTest extends BaseRepositoryTest<WorkoutSession> {

    private final static LocalDateTime NOW = LocalDateTime.now();

    @Override
    protected WorkoutSession forSave() {
        return WorkoutSession.builder()
                .isNew(true)
                .dateTime(NOW)
                .plan(workoutPlanSnapshot())
                .note("Легкая тренировка")
                .build();
    }

    @Override
    protected WorkoutSession expectedSaved(UUID id) {
        return WorkoutSession.builder()
                .id(id)
                .isNew(false)
                .dateTime(NOW)
                .plan(workoutPlanSnapshot())
                .note("Легкая тренировка")
                .build();
    }
}