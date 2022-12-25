package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.data.test.WorkoutPlanTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutPlanRepositoryTest extends BaseRepositoryTest<WorkoutPlan> {

    @Override
    protected TestDataStore<WorkoutPlan> entityStore() {
        return new WorkoutPlanTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutPlan> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutPlan.class,"id");
    }
}