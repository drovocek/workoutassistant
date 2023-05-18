package ru.soft.web.controller.integration;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;
import ru.soft.data.test.ExerciseTestDataStore;
import ru.soft.utils.MatcherFactory;

class ExerciseApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<Exercise, ExerciseTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.Exercises.URL;
    }

    @Override
    protected MatcherFactory.Matcher<ExerciseTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(ExerciseTo.class,"id");
    }

    @Override
    protected TestDataStore<Exercise> entityStore() {
        return new ExerciseTestDataStore();
    }

    @Override
    protected TestDataStore<ExerciseTo> toStore() {
        return new ExerciseToTestDataStore();
    }
}