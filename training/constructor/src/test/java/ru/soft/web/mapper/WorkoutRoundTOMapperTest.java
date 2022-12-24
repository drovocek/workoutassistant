package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.data.model.WorkoutRound;
import ru.soft.testdata.model.WorkoutRoundTestDataStore;

class WorkoutRoundTOMapperTest extends AbstractTOMapperTest<WorkoutRound, WorkoutRoundTo> {

    @Override
    TOMapper<WorkoutRound, WorkoutRoundTo> mapper() {
        return new WorkoutRoundTOMapper();
    }

    @Override
    TestDataStore<WorkoutRound> entityStore() {
        return new WorkoutRoundTestDataStore();
    }

    @Override
    TestDataStore<WorkoutRoundTo> toStore() {
        return new WorkoutRoundToTestDataStore();
    }
}