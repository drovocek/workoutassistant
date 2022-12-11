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

class WorkoutRoundSchemaSnapshotToPGobjectConverterTest {

    private WorkoutRoundSchemaToPGobjectConverter writingConverter;

    @BeforeEach
    void init() {
        this.writingConverter = new WorkoutRoundSchemaToPGobjectConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        List<WorkoutStationSnapshot> workoutStationSnapshots = createWorkoutStationSnapshots();
        WorkoutRoundSchemaSnapshot schema = new WorkoutRoundSchemaSnapshot(workoutStationSnapshots);
        PGobject pGobject = this.writingConverter.convert(schema);
        PGobject expectedPGobject = createWorkoutRoundSchemaPGobject();
        Assertions.assertEquals(expectedPGobject, pGobject);
    }
}