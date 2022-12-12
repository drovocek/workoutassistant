package ru.soft.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.data.model.snapshot.WorkoutRoundSnapshot;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.List;
import java.util.UUID;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

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
                .workoutSchemaSnapshot(
                        new WorkoutSchemaSnapshot(
                                List.of(
                                        WorkoutRoundSnapshot.builder()
                                                .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                                                .title("test title 1")
                                                .description("test description 1")
                                                .build(),
                                        WorkoutRoundSnapshot.builder()
                                                .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                                                .title("test title 2")
                                                .description("test description 2")
                                                .build()
                                )
                        ))
                .build();
    }

    @Override
    protected WorkoutPlan expectedSaved(UUID id) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(false)
                .title("test title")
                .description("test description")
                .workoutSchemaSnapshot(
                        new WorkoutSchemaSnapshot(
                                List.of(
                                        WorkoutRoundSnapshot.builder()
                                                .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                                                .title("test title 1")
                                                .description("test description 1")
                                                .build(),
                                        WorkoutRoundSnapshot.builder()
                                                .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                                                .title("test title 2")
                                                .description("test description 2")
                                                .build()
                                )
                        ))
                .build();
    }
}