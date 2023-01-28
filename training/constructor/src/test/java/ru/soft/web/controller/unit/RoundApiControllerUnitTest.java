package ru.soft.web.controller.unit;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.soft.common.AppApi;
import ru.soft.common.to.RoundTo;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.RoundToTestDataStore;
import ru.soft.utils.MatcherFactory;

@WebMvcTest
class RoundApiControllerUnitTest extends AbstractApiControllerUnitTest<RoundTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.Rounds.URL;
    }

    @Override
    protected MatcherFactory.Matcher<RoundTo> matcher() {
        return MatcherFactory.usingEqualsComparator(RoundTo.class);
    }

    @Override
    protected TestDataStore<RoundTo> toStore() {
        return new RoundToTestDataStore();
    }
}