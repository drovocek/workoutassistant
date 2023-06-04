package ru.soft.web.mapper;

import ru.soft.common.to.RoundTo;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.RoundToTestDataStore;
import ru.soft.data.model.Round;
import ru.soft.data.test.RoundDataStore;

class StationSetTOMapperTest extends AbstractTOMapperTest<Round, RoundTo> {

    @Override
    TOMapper<Round, RoundTo> mapper() {
        return new WorkoutTOMapper();
    }

    @Override
    TestDataStore<Round> entityStore() {
        return new RoundDataStore();
    }

    @Override
    TestDataStore<RoundTo> toStore() {
        return new RoundToTestDataStore();
    }
}