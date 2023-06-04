package ru.soft.web.controller.unit;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;
import ru.soft.common.to.ExerciseTo;
import ru.soft.utils.MatcherFactory;

class ExerciseApiControllerRestUnitTest extends AbstractApiControllerRestUnitTest<ExerciseTo> {

    @Override
    protected TestDataStore<ExerciseTo> toStore() {
        return new ExerciseToTestDataStore();
    }

    @Override
    protected String getApiUrl() {
        return AppApi.Exercises.URL;
    }

    @Override
    protected MatcherFactory.Matcher<ExerciseTo> matcher() {
        return MatcherFactory.usingEqualsComparator(ExerciseTo.class);
    }
}