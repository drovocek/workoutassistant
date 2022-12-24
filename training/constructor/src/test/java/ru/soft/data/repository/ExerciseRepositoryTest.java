package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Exercise;
import ru.soft.testdata.model.ExerciseTestDataStore;
import ru.soft.utils.MatcherFactory;

class ExerciseRepositoryTest extends BaseRepositoryTest<Exercise> {

    @Override
    protected TestDataStore<Exercise> entityStore() {
        return new ExerciseTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<Exercise> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(Exercise.class, "id");
    }
}