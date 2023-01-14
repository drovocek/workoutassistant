package ru.soft.web.controller.unit;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.common.to.RoundTo;
import ru.soft.utils.MatcherFactory;

class RoundApiControllerUnitTest extends AbstractApiControllerUnitTest<RoundTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutRounds.URL;
    }

    @Override
    protected MatcherFactory.Matcher<RoundTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(RoundTo.class);
    }

    @Override
    protected TestDataStore<RoundTo> toStore() {
        return new WorkoutRoundToTestDataStore();
    }
}