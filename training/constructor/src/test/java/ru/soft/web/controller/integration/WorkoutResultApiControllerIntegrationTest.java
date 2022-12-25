package ru.soft.web.controller.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutResultToTestDataStore;
import ru.soft.common.to.WorkoutResultTo;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.WorkoutResult;
import ru.soft.data.model.WorkoutSession;
import ru.soft.data.repository.BaseRepository;
import ru.soft.data.test.WorkoutResultTestDataStore;
import ru.soft.data.test.WorkoutSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class WorkoutResultApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<WorkoutResult, WorkoutResultTo> {

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
    protected List<WorkoutResultTo> expectedAll() {
        UUID[] sessionIds = this.sessionRepository.findAll().stream()
                .map(BaseEntity::id)
                .toArray(UUID[]::new);
        return toStore().entities(false, sessionIds);
    }

    @Override
    protected WorkoutResultTo expected(boolean isNew) {
        return toStore().entity(isNew, existSessionId());
    }

    @Override
    protected WorkoutResultTo requestEntity(boolean isNew) {
        return toStore().requestEntity(isNew, this.sessionRepository.findAll().get(2).id());
    }

    @Override
    protected List<WorkoutResultTo> invalids(boolean isNew) {
        return toStore().invalids(isNew, existSessionId());
    }

    @Override
    protected List<WorkoutResultTo> duplicates(boolean isNew) {
        return toStore().duplicates(isNew, existSessionId());
    }

    @Override
    protected List<WorkoutResultTo> htmlUnsafe(boolean isNew) {
        return toStore().htmlUnsafe(isNew, existSessionId());
    }

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutResults.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutResultTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutResultTo.class, "id");
    }

    @Override
    protected int rowsCount() {
        return 2;
    }

    @Override
    protected WorkoutResultTestDataStore entityStore() {
        return new WorkoutResultTestDataStore();
    }

    @Override
    protected WorkoutResultToTestDataStore toStore() {
        return new WorkoutResultToTestDataStore();
    }
}