package ru.soft.web.controller.integration;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.common.to.RoundTo;
import ru.soft.data.model.Round;
import ru.soft.data.test.WorkoutRoundTestDataStore;
import ru.soft.utils.MatcherFactory;

class RoundApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<Round, RoundTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutRounds.URL;
    }

    @Override
    protected MatcherFactory.Matcher<RoundTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(RoundTo.class,"id");
    }

    @Override
    protected TestDataStore<Round> entityStore() {
        return new WorkoutRoundTestDataStore();
    }

    @Override
    protected TestDataStore<RoundTo> toStore() {
        return new WorkoutRoundToTestDataStore();
    }
}