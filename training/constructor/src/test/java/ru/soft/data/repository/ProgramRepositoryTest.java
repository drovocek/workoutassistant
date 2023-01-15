package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Program;
import ru.soft.data.test.WorkoutSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

class ProgramRepositoryTest extends BaseRepositoryTest<Program> {

    @Override
    protected TestDataStore<Program> entityStore() {
        return new WorkoutSessionTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<Program> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(Program.class, "id");
    }
}