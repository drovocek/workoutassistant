package ru.soft.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import ru.soft.data.model.Exercise;

import java.util.UUID;

@DataJdbcTest
class ExerciseRepositoryTest extends BaseRepositoryTest<Exercise> {

    @Autowired
    private ExerciseRepository repository;

    @Override
    protected BaseRepository<Exercise> repository() {
        return this.repository;
    }

    @Override
    protected Exercise forSave() {
        return Exercise.builder()
                .isNew(true)
                .title("test title")
                .description("test description")
                .complexity(10)
                .build();
    }

    @Override
    protected Exercise expectedSaved(UUID id) {
        return Exercise.builder()
                .id(id)
                .isNew(false)
                .title("test title")
                .description("test description")
                .complexity(10)
                .build();
    }
}