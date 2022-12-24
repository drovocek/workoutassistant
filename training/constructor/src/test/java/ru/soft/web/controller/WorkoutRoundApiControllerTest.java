package ru.soft.web.controller;

import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutRoundApiControllerTest extends AbstractApiControllerTest<WorkoutRoundTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutRound.URL;
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