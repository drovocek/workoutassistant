package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutResultToTestDataStore;
import ru.soft.common.to.WorkoutResultTo;
import ru.soft.data.model.WorkoutResult;
import ru.soft.testdata.model.WorkoutResultTestDataStore;

class WorkoutResultTOMapperTest extends AbstractTOMapperTest<WorkoutResult, WorkoutResultTo> {

    @Override
    TOMapper<WorkoutResult, WorkoutResultTo> mapper() {
        return new WorkoutResultTOMapper();
    }

    @Override
    TestDataStore<WorkoutResult> entityStore() {
        return new WorkoutResultTestDataStore();
    }

    @Override
    TestDataStore<WorkoutResultTo> toStore() {
        return new WorkoutResultToTestDataStore();
    }
}