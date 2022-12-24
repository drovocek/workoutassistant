package ru.soft.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.WorkoutResult;
import ru.soft.data.model.WorkoutSession;
import ru.soft.testdata.model.WorkoutResultTestDataStore;
import ru.soft.testdata.model.WorkoutSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

import java.util.List;
import java.util.UUID;

class WorkoutResultRepositoryTest extends BaseRepositoryTest<WorkoutResult> {

    @Autowired
    private BaseRepository<WorkoutSession> sessionRepository;
    private final TestDataStore<WorkoutSession> workoutSessionTestDataStore = new WorkoutSessionTestDataStore();

    @BeforeEach
    @Override
    protected void populateDB() {
        this.sessionRepository.saveAll(this.workoutSessionTestDataStore.entities(true));
        this.repository.saveAll(all(true));
    }

    private UUID existSessionId() {
        return this.sessionRepository.findAll().get(0).id();
    }

    @Override
    protected List<WorkoutResult> all(boolean isNew) {
        UUID[] sessionIds = this.sessionRepository.findAll().stream()
                .map(BaseEntity::id)
                .toArray(UUID[]::new);
        return entityStore().entities(isNew, sessionIds);
    }

    @Override
    protected WorkoutResult requestEntity(boolean isNew) {
        return entityStore().requestEntity(isNew, this.sessionRepository.findAll().get(2).id());
    }

    @Override
    protected List<WorkoutResult> invalids(boolean isNew) {
        return entityStore().invalids(isNew, existSessionId());
    }

    @Override
    protected List<WorkoutResult> duplicates(boolean isNew) {
        return entityStore().duplicates(isNew, existSessionId());
    }

    @Override
    protected List<WorkoutResult> htmlUnsafe(boolean isNew) {
        return entityStore().htmlUnsafe(isNew, existSessionId());
    }

    @Override
    protected WorkoutResultTestDataStore entityStore() {
        return new WorkoutResultTestDataStore();
    }

    @Override
    protected int rowsCount() {
        return 2;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutResult> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutResult.class, "id");
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