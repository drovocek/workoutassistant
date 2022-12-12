package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.TestContainerHolder;
import ru.soft.data.model.Exercise;

import java.util.List;
import java.util.Optional;

@DataJdbcTest
class ExerciseRepositoryTest extends TestContainerHolder {

    @Autowired
    private ExerciseRepository repository;

    @Test
    void getAll() {
        List<Exercise> exercises = this.repository.findAll();
        Assertions.assertEquals(3, exercises.size());
    }

    @Test
    void addNew() {
        Exercise exercise = Exercise.builder()
                .isNew(true)
                .title("test title")
                .description("test description")
                .complexity(10)
                .build();
        Exercise saved = this.repository.save(exercise);
        Assertions.assertNotNull(saved.getId());
        Optional<Exercise> exerciseOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(exerciseOpt.isPresent());
        Assertions.assertEquals(exerciseOpt.get(), saved);
    }
}