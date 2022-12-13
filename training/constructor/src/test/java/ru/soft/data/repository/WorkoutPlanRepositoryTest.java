package ru.soft.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.data.model.WorkoutPlan;

import java.util.UUID;

import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaSnapshot;

@DataJdbcTest
class WorkoutPlanRepositoryTest extends BaseRepositoryTest<WorkoutPlan> {

    @Autowired
    private WorkoutPlanRepository repository;

    @Override
    protected BaseRepository<WorkoutPlan> repository() {
        return this.repository;
    }

    @Override
    protected WorkoutPlan forSave() {
        return WorkoutPlan.builder()
                .isNew(true)
                .title("test title")
                .description("test description")
                .workoutSchemaSnapshot(createWorkoutSchemaSnapshot())
                .build();
    }

    @Override
    protected WorkoutPlan expectedSaved(UUID id) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(false)
                .title("test title")
                .description("test description")
                .workoutSchemaSnapshot(createWorkoutSchemaSnapshot())
                .build();
    }
}