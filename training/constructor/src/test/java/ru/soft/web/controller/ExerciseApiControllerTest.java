package ru.soft.web.controller;

import ru.soft.common.AppApi;
import ru.soft.common.to.ExerciseTo;
import ru.soft.testdata.to.ExerciseToTestDataStore;
import ru.soft.utils.MatcherFactory;

class ExerciseApiControllerTest extends AbstractApiControllerTest<ExerciseTo> {

    @Override
    protected TestToStore<ExerciseTo> toStore() {
        return new ExerciseToTestDataStore();
    }

    @Override
    protected String getApiUrl() {
        return AppApi.Exercise.URL;
    }

    @Override
    protected MatcherFactory.Matcher<ExerciseTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(ExerciseTo.class);
    }
}