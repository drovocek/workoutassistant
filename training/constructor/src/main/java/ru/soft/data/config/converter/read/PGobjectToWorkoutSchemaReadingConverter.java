package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ru.soft.common.data.elements.WorkoutSchema;

@RequiredArgsConstructor
public class PGobjectToWorkoutSchemaReadingConverter implements Converter<PGobject, WorkoutSchema> {

    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public WorkoutSchema convert(@NonNull PGobject source) {
        String workoutSchemaJson = source.getValue();
        return this.mapper.readValue(workoutSchemaJson, WorkoutSchema.class);
    }
}