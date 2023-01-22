package ru.soft.web.controller.integration;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutToTestDataStore;
import ru.soft.common.to.WorkoutTo;
import ru.soft.data.model.Workout;
import ru.soft.data.test.WorkoutTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<Workout, WorkoutTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.Workouts.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutTo.class,"id");
    }

    @Override
    protected TestDataStore<Workout> entityStore() {
        return new WorkoutTestDataStore();
    }

    @Override
    protected TestDataStore<WorkoutTo> toStore() {
        return new WorkoutToTestDataStore();
    }
}