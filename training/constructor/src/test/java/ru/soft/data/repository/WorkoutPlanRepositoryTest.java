package ru.soft.data.repository;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.data.model.WorkoutPlan;

import java.util.UUID;

import static ru.soft.testdata.WorkoutPlanTestDataStore.workoutSchemaSnapshot;

@DataJdbcTest
class WorkoutPlanRepositoryTest extends BaseRepositoryTest<WorkoutPlan> {

    @Override
    protected WorkoutPlan forSave() {
        return WorkoutPlan.builder()
                .isNew(true)
                .title("test title")
                .description("test description")
                .workoutSchemaSnapshot(workoutSchemaSnapshot())
                .build();
    }

    @Override
    protected WorkoutPlan expectedSaved(UUID id) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(false)
                .title("test title")
                .description("test description")
                .workoutSchemaSnapshot(workoutSchemaSnapshot())
                .build();
    }
}