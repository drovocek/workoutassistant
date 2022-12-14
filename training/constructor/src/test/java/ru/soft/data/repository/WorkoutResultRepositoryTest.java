package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import ru.soft.data.model.WorkoutResult;

import java.util.UUID;

import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaSnapshot;

@DataJdbcTest
class WorkoutResultRepositoryTest extends BaseRepositoryTest<WorkoutResult> {

    @Override
    protected int rowsCount() {
        return 2;
    }

    @Override
    protected WorkoutResult forSave() {
        return forSave(UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002"));
    }

    private WorkoutResult forSave(UUID workoutSessionId) {
        return WorkoutResult.builder()
                .workoutSessionId(workoutSessionId)
                .isNew(true)
                .title("test title")
                .description("test description")
                .workoutSchemaSnapshot(createWorkoutSchemaSnapshot())
                .build();
    }

    @Override
    protected WorkoutResult expectedSaved(UUID id) {
        return expectedSaved(id, UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002"));
    }

    private WorkoutResult expectedSaved(UUID id, UUID workoutSessionId) {
        return WorkoutResult.builder()
                .workoutSessionId(workoutSessionId)
                .id(id)
                .isNew(false)
                .title("test title")
                .description("test description")
                .workoutSchemaSnapshot(createWorkoutSchemaSnapshot())
                .build();
    }

    @Test
    @DisplayName("Для одной сессии можно добавить только один результат")
    void oneSessionToOneResult() {
        UUID workoutSessionId = UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002");
        DbActionExecutionException exception = Assertions.assertThrows(DbActionExecutionException.class,
                () -> save(forSave(workoutSessionId), id -> expectedSaved(id, workoutSessionId)));
        String expected = "PreparedStatementCallback; SQL [INSERT INTO \"workout_result\" (\"description\", \"id\", \"title\", \"workout_schema\", \"workout_session_id\") VALUES (?, ?, ?, ?, ?)]; ERROR: duplicate key value violates unique constraint \"workout_result_workout_session_id_key\"\n" +
                "  Подробности: Key (workout_session_id)=(a34798c2-7ac8-11ed-a1eb-0242ac120002) already exists.";
        Assertions.assertEquals(expected, exception.getCause().getMessage());
    }

    @Test
    @DisplayName("Результат можно добавить только для существующей сессии")
    void noSessionNoResult() {
        UUID workoutSessionId = UUID.fromString("a34798c6-7ac8-11ed-a1eb-0242ac120002");
        DbActionExecutionException exception = Assertions.assertThrows(DbActionExecutionException.class,
                () -> save(forSave(workoutSessionId), id -> expectedSaved(id, workoutSessionId)));
        String expected = "PreparedStatementCallback; SQL [INSERT INTO \"workout_result\" (\"description\", \"id\", \"title\", \"workout_schema\", \"workout_session_id\") VALUES (?, ?, ?, ?, ?)]; ERROR: insert or update on table \"workout_result\" violates foreign key constraint \"workout_result_workout_session_id_fkey\"\n" +
                "  Подробности: Key (workout_session_id)=(a34798c6-7ac8-11ed-a1eb-0242ac120002) is not present in table \"workout_session\".";
        Assertions.assertEquals(expected, exception.getCause().getMessage());
    }
}