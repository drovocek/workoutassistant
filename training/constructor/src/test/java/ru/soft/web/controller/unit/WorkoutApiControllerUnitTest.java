package ru.soft.web.controller.unit;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutTo;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.utils.MatcherFactory;

@WebMvcTest
class WorkoutApiControllerUnitTest extends AbstractApiControllerUnitTest<WorkoutTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutPlans.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutTo.class);
    }

    @Override
    protected TestDataStore<WorkoutTo> toStore() {
        return new WorkoutPlanToTestDataStore();
    }
}