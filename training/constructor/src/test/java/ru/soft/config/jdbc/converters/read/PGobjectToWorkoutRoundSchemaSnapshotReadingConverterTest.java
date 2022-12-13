package ru.soft.config.jdbc.converters.read;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.config.jdbc.JdbcConfig;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

class PGobjectToWorkoutRoundSchemaSnapshotReadingConverterTest {

    private PGobjectToWorkoutRoundSchemaSnapshotReadingConverter readingConverter;

    @BeforeEach
    void init() {
        this.readingConverter = new PGobjectToWorkoutRoundSchemaSnapshotReadingConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutRoundSchemaSnapshot expectedSchema = createWorkoutRoundSchemaSnapshot();
        PGobject pGobject = createWorkoutRoundSchemaPGobject();
        WorkoutRoundSchemaSnapshot actualSchema = this.readingConverter.convert(pGobject);
        Assertions.assertEquals(expectedSchema, actualSchema);
    }
}