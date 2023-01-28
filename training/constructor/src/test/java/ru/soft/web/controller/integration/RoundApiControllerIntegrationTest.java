package ru.soft.web.controller.integration;

import ru.soft.common.AppApi;
import ru.soft.common.to.RoundTo;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.RoundToTestDataStore;
import ru.soft.data.model.Round;
import ru.soft.data.test.RoundDataStore;
import ru.soft.utils.MatcherFactory;

class RoundApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<Round, RoundTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.Rounds.URL;
    }

    @Override
    protected MatcherFactory.Matcher<RoundTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(RoundTo.class,"id");
    }

    @Override
    protected TestDataStore<Round> entityStore() {
        return new RoundDataStore();
    }

    @Override
    protected TestDataStore<RoundTo> toStore() {
        return new RoundToTestDataStore();
    }
}