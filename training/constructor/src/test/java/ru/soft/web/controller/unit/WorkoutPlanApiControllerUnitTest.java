package ru.soft.web.controller.unit;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.utils.MatcherFactory;

@WebMvcTest
class WorkoutPlanApiControllerUnitTest extends AbstractApiControllerUnitTest<WorkoutPlanTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutPlans.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutPlanTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutPlanTo.class);
    }

    @Override
    protected TestDataStore<WorkoutPlanTo> toStore() {
        return new WorkoutPlanToTestDataStore();
    }
}