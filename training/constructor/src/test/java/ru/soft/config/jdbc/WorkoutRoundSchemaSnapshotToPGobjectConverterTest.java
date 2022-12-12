package ru.soft.config.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

class WorkoutRoundSchemaSnapshotToPGobjectConverterTest {

    private WorkoutRoundSchemaSnapshotToPGobjectConverter writingConverter;

    @BeforeEach
    void init() {
        this.writingConverter = new WorkoutRoundSchemaSnapshotToPGobjectConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutRoundSchemaSnapshot schema = createWorkoutRoundSchemaSnapshot();
        PGobject pGobject = this.writingConverter.convert(schema);
        PGobject expectedPGobject = createWorkoutRoundSchemaPGobject();
        Assertions.assertEquals(expectedPGobject, pGobject);
    }
}