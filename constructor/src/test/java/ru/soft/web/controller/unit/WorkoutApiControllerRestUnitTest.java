package ru.soft.web.controller.unit;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutToTestDataStore;
import ru.soft.common.to.WorkoutTo;
import ru.soft.utils.MatcherFactory;

class WorkoutApiControllerRestUnitTest extends AbstractApiControllerRestUnitTest<WorkoutTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.Workouts.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutTo> matcher() {
        return MatcherFactory.usingEqualsComparator(WorkoutTo.class);
    }

    @Override
    protected TestDataStore<WorkoutTo> toStore() {
        return new WorkoutToTestDataStore();
    }
}