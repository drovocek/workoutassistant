package ru.soft.web.controller;

import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutPlanApiControllerTest extends AbstractApiControllerTest<WorkoutPlanTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutPlan.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutPlanTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutPlanTo.class);
    }

    @Override
    protected TestToStore<WorkoutPlanTo> toStore() {
        return new WorkoutPlanToTestDataStore();
    }
}