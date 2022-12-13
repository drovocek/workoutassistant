package ru.soft.data.config.converters.read;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.data.config.JdbcConfig;
import ru.soft.data.model.snapshot.WorkoutPlanSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutPlanPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutPlanSnapshot;

class PGobjectToWorkoutPlanSnapshotReadingConverterTest {

    private PGobjectToWorkoutPlanSnapshotReadingConverter readingConverter;

    @BeforeEach
    void init() {
        this.readingConverter = new PGobjectToWorkoutPlanSnapshotReadingConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutPlanSnapshot expectedSchema = createWorkoutPlanSnapshot();
        PGobject pGobject = createWorkoutPlanPGobject();
        WorkoutPlanSnapshot actualSchema = this.readingConverter.convert(pGobject);
        Assertions.assertEquals(expectedSchema, actualSchema);
    }
}