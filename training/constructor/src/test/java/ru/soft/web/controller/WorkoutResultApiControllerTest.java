package ru.soft.web.controller;

import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutResultTo;
import ru.soft.testdata.to.WorkoutResultToTestDataStore;
import ru.soft.utils.MatcherFactory;


class WorkoutResultApiControllerTest extends AbstractApiControllerTest<WorkoutResultTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutResult.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutResultTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutResultTo.class);
    }

    @Override
    protected int rowsCount() {
        return 2;
    }

    @Override
    protected TestToStore<WorkoutResultTo> toStore() {
        return new WorkoutResultToTestDataStore();
    }
}