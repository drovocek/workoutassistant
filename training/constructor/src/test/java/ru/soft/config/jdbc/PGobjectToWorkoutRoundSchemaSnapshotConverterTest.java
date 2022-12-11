package ru.soft.config.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.data.model.snapshot.WorkoutStationSnapshot;

import java.sql.SQLException;
import java.util.List;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutStationSnapshots;

class PGobjectToWorkoutRoundSchemaSnapshotConverterTest {

    private PGobjectToWorkoutRoundSchemaConverter readingConverter;

    @BeforeEach
    void init() {
        this.readingConverter = new PGobjectToWorkoutRoundSchemaConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        List<WorkoutStationSnapshot> workoutStationSnapshots = createWorkoutStationSnapshots();
        WorkoutRoundSchemaSnapshot expectedSchema = new WorkoutRoundSchemaSnapshot(workoutStationSnapshots);
        PGobject pGobject = createWorkoutRoundSchemaPGobject();
        WorkoutRoundSchemaSnapshot actualSchema = this.readingConverter.convert(pGobject);
        Assertions.assertEquals(expectedSchema, actualSchema);
    }
}