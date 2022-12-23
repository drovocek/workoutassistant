package ru.soft.data.repository;

import ru.soft.data.model.WorkoutResult;
import ru.soft.testdata.TestDataStore;
import ru.soft.testdata.model.WorkoutResultTestDataStore;

class WorkoutResultRepositoryTest extends BaseRepositoryTest<WorkoutResult> {

    @Override
    protected TestDataStore<WorkoutResult> entityStore() {
        return new WorkoutResultTestDataStore();
    }

    @Override
    protected int rowsCount() {
        return 2;
    }
//
//    @Test
//    @DisplayName("Для одной сессии можно добавить только один результат")
//    void oneSessionToOneResult() {
//        UUID workoutSessionId = UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002");
//        DbActionExecutionException exception = Assertions.assertThrows(DbActionExecutionException.class,
//                () -> save(forSave(workoutSessionId), id -> expectedSaved(id, workoutSessionId)));
//        String expected = "PreparedStatementCallback; SQL [INSERT INTO \"workout_result\" (\"description\", \"id\", \"title\", \"workout_schema\", \"workout_session_id\") VALUES (?, ?, ?, ?, ?)]; ERROR: duplicate key value violates unique constraint \"workout_result_workout_session_id_key\"\n" +
//                "  Подробности: Key (workout_session_id)=(a34798c2-7ac8-11ed-a1eb-0242ac120002) already exists.";
//        Assertions.assertEquals(expected, exception.getCause().getMessage());
//    }
//
//    @Test
//    @DisplayName("Результат можно добавить только для существующей сессии")
//    void noSessionNoResult() {
//        UUID workoutSessionId = UUID.fromString("a34798c6-7ac8-11ed-a1eb-0242ac120002");
//        DbActionExecutionException exception = Assertions.assertThrows(DbActionExecutionException.class,
//                () -> save(forSave(workoutSessionId), id -> expectedSaved(id, workoutSessionId)));
//        String expected = "PreparedStatementCallback; SQL [INSERT INTO \"workout_result\" (\"description\", \"id\", \"title\", \"workout_schema\", \"workout_session_id\") VALUES (?, ?, ?, ?, ?)]; ERROR: insert or update on table \"workout_result\" violates foreign key constraint \"workout_result_workout_session_id_fkey\"\n" +
//                "  Подробности: Key (workout_session_id)=(a34798c6-7ac8-11ed-a1eb-0242ac120002) is not present in table \"workout_session\".";
//        Assertions.assertEquals(expected, exception.getCause().getMessage());
//    }
}