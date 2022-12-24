package ru.soft.web.controller.integration;

import org.springframework.boot.test.context.SpringBootTest;
import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.data.model.WorkoutRound;
import ru.soft.testdata.model.WorkoutRoundTestDataStore;
import ru.soft.utils.MatcherFactory;

@SpringBootTest
class WorkoutRoundApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<WorkoutRound, WorkoutRoundTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutRounds.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutRoundTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutRoundTo.class,"id");
    }

    @Override
    protected TestDataStore<WorkoutRound> entityStore() {
        return new WorkoutRoundTestDataStore();
    }

    @Override
    protected TestDataStore<WorkoutRoundTo> toStore() {
        return new WorkoutRoundToTestDataStore();
    }
}