package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.soft.AbstractDataTest;
import ru.soft.data.model.Exercise;

import java.util.List;

@SpringBootTest
class ExerciseRepositoryTest extends AbstractDataTest {

    @Autowired
    private ExerciseRepository repository;

    @Test
    void getAll() {
        List<Exercise> exercises = repository.findAll();
        Assertions.assertEquals(3, exercises.size());
    }
}