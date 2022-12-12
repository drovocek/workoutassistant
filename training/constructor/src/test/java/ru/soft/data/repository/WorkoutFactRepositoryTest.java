package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.AbstractDataTest;
import ru.soft.data.model.WorkoutFact;
import ru.soft.data.model.snapshot.WorkoutRoundSnapshot;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.List;
import java.util.Optional;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

@DataJdbcTest
class WorkoutFactRepositoryTest extends AbstractDataTest {

    @Autowired
    private WorkoutFactRepository repository;

    @Test
    void addNew() {
        WorkoutFact workoutPlan =
                WorkoutFact.builder()
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
        WorkoutFact saved = this.repository.save(workoutPlan);
        Assertions.assertNotNull(saved.getId());

        Optional<WorkoutFact> workoutPlanOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(workoutPlanOpt.isPresent());

        WorkoutFact actual = workoutPlanOpt.get();
        WorkoutFact expected = WorkoutFact.builder()
                .id(saved.id())
                .isNew(false)
                .workoutSchemaSnapshot(saved.workoutSchemaSnapshot())
                .title(saved.title())
                .description(saved.description())
                .build();
        Assertions.assertEquals(expected, actual);
    }
}