package ru.soft.webclient.endpoint;

import org.springframework.beans.factory.annotation.Value;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;
import ru.soft.common.to.ExerciseTo;

public class ExerciseEndpointTest extends BaseEndpointTest<ExerciseTo>{

    @Value("#{T(ru.soft.common.AppApi.Exercises).URL}")
    private String rootUrl;

    @Override
    protected String rootUrl() {
        return this.rootUrl;
    }

    @Override
    protected TestDataStore<ExerciseTo> dataStore() {
        return new ExerciseToTestDataStore();
    }
}
