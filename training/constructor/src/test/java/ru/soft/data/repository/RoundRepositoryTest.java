package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Round;
import ru.soft.data.test.WorkoutRoundTestDataStore;
import ru.soft.utils.MatcherFactory;

class RoundRepositoryTest extends BaseRepositoryTest<Round> {

    @Override
    protected TestDataStore<Round> entityStore() {
        return new WorkoutRoundTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<Round> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(Round.class, "id");
    }
}