package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.postgresql.util.PGobject;
import ru.soft.common.data.elements.WorkoutSchema;
import ru.soft.data.config.converter.CustomJsonObjectMapper;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

import static ru.soft.common.testdata.TestSchemaStore.workoutSchema;

class PGobjectToWorkoutSchemaReadingConverterTest {

    private final ObjectMapper jsonMapper = CustomJsonObjectMapper.instance();
    private final PGobjectToWorkoutSchemaReadingConverter readingConverter = new PGobjectToWorkoutSchemaReadingConverter(jsonMapper);

    static Stream<WorkoutSchema> schemaGenerator() {
        return Stream.of(
                workoutSchema()
        );
    }

    @ParameterizedTest
    @MethodSource("schemaGenerator")
    void convert(WorkoutSchema expected) {
        PGobject pGobject = PGobject(expected);
        WorkoutSchema actual = this.readingConverter.convert(pGobject);
        Assertions.assertEquals(expected, actual);
    }

    public PGobject PGobject(WorkoutSchema workoutSchema) {
        return wrapper(
                () -> {
                    PGobject pGobject = new PGobject();
                    pGobject.setType("jsonb");
                    String json = this.jsonMapper.writeValueAsString(workoutSchema);
                    pGobject.setValue(json);
                    return pGobject;
                }
        );
    }

    private PGobject wrapper(Callable<PGobject> pGobjectSupplier) {
        try {
            return pGobjectSupplier.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}