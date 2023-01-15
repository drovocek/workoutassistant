package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutSessionToTestDataStore;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.Program;
import ru.soft.data.test.WorkoutSessionTestDataStore;

class ProgramTOMapperTest extends AbstractTOMapperTest<Program, WorkoutSessionTo> {

    @Override
    TOMapper<Program, WorkoutSessionTo> mapper() {
        return new WorkoutSessionTOMapper();
    }

    @Override
    TestDataStore<Program> entityStore() {
        return new WorkoutSessionTestDataStore();
    }

    @Override
    TestDataStore<WorkoutSessionTo> toStore() {
        return new WorkoutSessionToTestDataStore();
    }
}