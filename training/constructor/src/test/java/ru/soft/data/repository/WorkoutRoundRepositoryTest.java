package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.WorkoutRound;
import ru.soft.data.test.WorkoutRoundTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutRoundRepositoryTest extends BaseRepositoryTest<WorkoutRound> {

    @Override
    protected TestDataStore<WorkoutRound> entityStore() {
        return new WorkoutRoundTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutRound> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutRound.class, "id");
    }
}