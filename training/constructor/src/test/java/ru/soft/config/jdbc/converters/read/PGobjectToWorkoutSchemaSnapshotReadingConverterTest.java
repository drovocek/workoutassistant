package ru.soft.config.jdbc.converters.read;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.config.jdbc.JdbcConfig;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaSnapshot;

class PGobjectToWorkoutSchemaSnapshotReadingConverterTest {

    private PGobjectToWorkoutSchemaSnapshotReadingConverter readingConverter;

    @BeforeEach
    void init() {
        this.readingConverter = new PGobjectToWorkoutSchemaSnapshotReadingConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutSchemaSnapshot expectedSchema = createWorkoutSchemaSnapshot();
        PGobject pGobject = createWorkoutSchemaPGobject();
        WorkoutSchemaSnapshot actualSchema = this.readingConverter.convert(pGobject);
        Assertions.assertEquals(expectedSchema, actualSchema);
    }
}