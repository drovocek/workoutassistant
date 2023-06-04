package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Round;
import ru.soft.data.test.RoundDataStore;
import ru.soft.utils.MatcherFactory;

class StationSetRepositoryTest extends BaseRepositoryTest<Round> {

    @Override
    protected TestDataStore<Round> entityStore() {
        return new RoundDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<Round> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(Round.class,"id");
    }
}