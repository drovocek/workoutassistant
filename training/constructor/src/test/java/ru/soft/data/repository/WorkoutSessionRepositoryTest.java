package ru.soft.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.data.model.WorkoutSession;

import java.time.LocalDateTime;
import java.util.UUID;

import static ru.soft.utils.JsonTestUtils.createWorkoutPlanSnapshot;

@DataJdbcTest
class WorkoutSessionRepositoryTest extends BaseRepositoryTest<WorkoutSession> {

    private final static LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private WorkoutSessionRepository repository;

    @Override
    protected BaseRepository<WorkoutSession> repository() {
        return this.repository;
    }

    @Override
    protected WorkoutSession forSave() {
        return WorkoutSession.builder()
                .isNew(true)
                .dateTime(NOW)
                .plan(createWorkoutPlanSnapshot())
                .note("Легкая тренировка")
                .build();
    }

    @Override
    protected WorkoutSession expectedSaved(UUID id) {
        return WorkoutSession.builder()
                .id(id)
                .isNew(false)
                .dateTime(NOW)
                .plan(createWorkoutPlanSnapshot())
                .note("Легкая тренировка")
                .build();
    }
}