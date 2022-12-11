package ru.soft.config.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchema;
import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaPGobject;

class PGobjectToWorkoutRoundSchemaSnapshotConverterTest {

    private PGobjectToWorkoutRoundSchemaConverter readingConverter;

    @BeforeEach
    void init() {
        this.readingConverter = new PGobjectToWorkoutRoundSchemaConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutRoundSchemaSnapshot expectedSchema = createWorkoutRoundSchema();
        PGobject pGobject = createWorkoutRoundSchemaPGobject();
        WorkoutRoundSchemaSnapshot actualSchema = this.readingConverter.convert(pGobject);
        Assertions.assertEquals(expectedSchema, actualSchema);
    }
}