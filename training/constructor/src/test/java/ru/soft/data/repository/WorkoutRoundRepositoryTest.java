package ru.soft.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.data.model.WorkoutRound;

import java.util.UUID;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

@DataJdbcTest
class WorkoutRoundRepositoryTest extends BaseRepositoryTest<WorkoutRound> {

    @Autowired
    private WorkoutRoundRepository repository;

    @Override
    protected BaseRepository<WorkoutRound> repository() {
        return this.repository;
    }

    @Override
    protected WorkoutRound forSave() {
        return WorkoutRound.builder()
                .isNew(true)
                .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                .title("test title")
                .description("test description")
                .build();
    }

    @Override
    protected WorkoutRound expectedSaved(UUID id) {
        return WorkoutRound.builder()
                .id(id)
                .isNew(false)
                .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                .title("test title")
                .description("test description")
                .build();
    }
}