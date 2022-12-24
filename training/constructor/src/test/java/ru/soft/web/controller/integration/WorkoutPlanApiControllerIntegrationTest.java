package ru.soft.web.controller.integration;

import org.springframework.boot.test.context.SpringBootTest;
import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.testdata.model.WorkoutPlanTestDataStore;
import ru.soft.utils.MatcherFactory;

@SpringBootTest
class WorkoutPlanApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<WorkoutPlan, WorkoutPlanTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutPlans.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutPlanTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutPlanTo.class,"id");
    }

    @Override
    protected TestDataStore<WorkoutPlan> entityStore() {
        return new WorkoutPlanTestDataStore();
    }

    @Override
    protected TestDataStore<WorkoutPlanTo> toStore() {
        return new WorkoutPlanToTestDataStore();
    }
}