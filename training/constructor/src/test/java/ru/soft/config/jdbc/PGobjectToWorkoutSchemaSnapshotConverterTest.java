package ru.soft.config.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaSnapshot;

class PGobjectToWorkoutSchemaSnapshotConverterTest {

    private PGobjectToWorkoutSchemaSnapshotConverter readingConverter;

    @BeforeEach
    void init() {
        this.readingConverter = new PGobjectToWorkoutSchemaSnapshotConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutSchemaSnapshot expectedSchema = createWorkoutSchemaSnapshot();
        PGobject pGobject = createWorkoutSchemaPGobject();
        WorkoutSchemaSnapshot actualSchema = this.readingConverter.convert(pGobject);
        Assertions.assertEquals(expectedSchema, actualSchema);
    }
}