package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.AbstractDataTest;
import ru.soft.data.model.WorkoutRound;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.utils.JsonTestUtils;

import java.util.Optional;

@DataJdbcTest
class WorkoutRoundRepositoryTest extends AbstractDataTest {

    @Autowired
    private WorkoutRoundRepository repository;

    @Test
    void addNew() {
        WorkoutRoundSchemaSnapshot roundSchema = JsonTestUtils.createWorkoutRoundSchema();
        WorkoutRound round = WorkoutRound.builder()
                .isNew(true)
                .schema(roundSchema)
                .title("test title")
                .description("test description")
                .complexity(10)
                .build();
        WorkoutRound saved = this.repository.save(round);
        Assertions.assertNotNull(saved.getId());
        Optional<WorkoutRound> workoutRoundOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(workoutRoundOpt.isPresent());
        Assertions.assertEquals(workoutRoundOpt.get(), saved);
    }
}