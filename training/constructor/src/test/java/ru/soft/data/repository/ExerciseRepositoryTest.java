package ru.soft.data.repository;

import ru.soft.data.model.Exercise;
import ru.soft.testdata.model.ExerciseTestDataStore;
import ru.soft.testdata.TestDataStore;

class ExerciseRepositoryTest extends BaseRepositoryTest<Exercise> {

    @Override
    protected TestDataStore<Exercise> entityStore() {
        return new ExerciseTestDataStore();
    }
}