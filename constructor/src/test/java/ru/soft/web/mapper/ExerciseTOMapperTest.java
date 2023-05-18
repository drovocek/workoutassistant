package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;
import ru.soft.data.test.ExerciseTestDataStore;

class ExerciseTOMapperTest extends AbstractTOMapperTest<Exercise, ExerciseTo> {

    @Override
    TOMapper<Exercise, ExerciseTo> mapper() {
        return new ExerciseTOMapper();
    }

    @Override
    TestDataStore<Exercise> entityStore() {
        return new ExerciseTestDataStore();
    }

    @Override
    TestDataStore<ExerciseTo> toStore() {
        return new ExerciseToTestDataStore();
    }
}