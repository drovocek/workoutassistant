package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Workout;
import ru.soft.data.test.WorkoutPlanTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutRepositoryTest extends BaseRepositoryTest<Workout> {

    @Override
    protected TestDataStore<Workout> entityStore() {
        return new WorkoutPlanTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<Workout> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(Workout.class,"id");
    }
}