package ru.soft.data.config.converter.write;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import ru.soft.data.config.JdbcConfig;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.sql.SQLException;

import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaSnapshot;

class WorkoutSchemaSnapshotToPGobjectWritingConverterTest {

    private WorkoutSchemaSnapshotToPGobjectWritingConverter writingConverter;

    @BeforeEach
    void init() {
        this.writingConverter = new WorkoutSchemaSnapshotToPGobjectWritingConverter(JdbcConfig.jdbcObjectMapper());
    }

    @Test
    void convert() throws SQLException {
        WorkoutSchemaSnapshot schema = createWorkoutSchemaSnapshot();
        PGobject pGobject = this.writingConverter.convert(schema);
        PGobject expectedPGobject = createWorkoutSchemaPGobject();
        Assertions.assertEquals(expectedPGobject, pGobject);
    }
}