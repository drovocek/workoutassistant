package ru.soft.web.controller;

import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.testdata.to.WorkoutSessionToTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutSessionApiControllerTest extends AbstractApiControllerTest<WorkoutSessionTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutSession.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutSessionTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutSessionTo.class);
    }

    @Override
    protected TestToStore<WorkoutSessionTo> toStore() {
        return new WorkoutSessionToTestDataStore();
    }
}