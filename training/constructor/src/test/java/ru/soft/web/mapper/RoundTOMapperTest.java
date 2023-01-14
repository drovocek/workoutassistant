package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.common.to.RoundTo;
import ru.soft.data.model.Round;
import ru.soft.data.test.WorkoutRoundTestDataStore;

class RoundTOMapperTest extends AbstractTOMapperTest<Round, RoundTo> {

    @Override
    TOMapper<Round, RoundTo> mapper() {
        return new WorkoutRoundTOMapper();
    }

    @Override
    TestDataStore<Round> entityStore() {
        return new WorkoutRoundTestDataStore();
    }

    @Override
    TestDataStore<RoundTo> toStore() {
        return new WorkoutRoundToTestDataStore();
    }
}