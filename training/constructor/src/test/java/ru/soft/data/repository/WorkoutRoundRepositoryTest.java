package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.AbstractDataTest;
import ru.soft.data.model.WorkoutRound;

import java.util.Optional;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

@DataJdbcTest
class WorkoutRoundRepositoryTest extends AbstractDataTest {

    @Autowired
    private WorkoutRoundRepository repository;

    @Test
    void addNew() {
        WorkoutRound round = WorkoutRound.builder()
                .isNew(true)
                .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                .title("test title")
                .description("test description")
                .build();
        WorkoutRound saved = this.repository.save(round);
        Assertions.assertNotNull(saved.getId());

        Optional<WorkoutRound> workoutRoundOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(workoutRoundOpt.isPresent());

        WorkoutRound actual = workoutRoundOpt.get();
        WorkoutRound expected = WorkoutRound.builder()
                .id(saved.id())
                .isNew(false)
                .workoutRoundSchemaSnapshot(saved.workoutRoundSchemaSnapshot())
                .title(saved.title())
                .description(saved.description())
                .build();
        Assertions.assertEquals(expected, actual);
    }
}