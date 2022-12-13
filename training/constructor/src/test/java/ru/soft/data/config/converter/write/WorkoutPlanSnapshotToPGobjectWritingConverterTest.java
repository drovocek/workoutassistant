package ru.soft.data.config.converter.write;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.data.config.JdbcConfig;
import ru.soft.data.model.snapshot.WorkoutPlanSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutPlanPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutPlanSnapshot;

class WorkoutPlanSnapshotToPGobjectWritingConverterTest {

    private WorkoutPlanSnapshotToPGobjectWritingConverter writingConverter;

    @BeforeEach
    void init() {
        this.writingConverter = new WorkoutPlanSnapshotToPGobjectWritingConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutPlanSnapshot schema = createWorkoutPlanSnapshot();
        PGobject pGobject = this.writingConverter.convert(schema);
        PGobject expectedPGobject = createWorkoutPlanPGobject();
        Assertions.assertEquals(expectedPGobject, pGobject);
    }
}