package ru.soft.web.controller.unit;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.utils.MatcherFactory;

class WorkoutRoundApiControllerUnitTest extends AbstractApiControllerUnitTest<WorkoutRoundTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutRounds.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutRoundTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutRoundTo.class);
    }

    @Override
    protected TestDataStore<WorkoutRoundTo> toStore() {
        return new WorkoutRoundToTestDataStore();
    }
}