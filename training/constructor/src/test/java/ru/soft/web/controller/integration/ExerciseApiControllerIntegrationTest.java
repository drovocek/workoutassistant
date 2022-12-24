package ru.soft.web.controller.integration;

import org.springframework.boot.test.context.SpringBootTest;
import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;
import ru.soft.testdata.model.ExerciseTestDataStore;
import ru.soft.utils.MatcherFactory;

@SpringBootTest
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