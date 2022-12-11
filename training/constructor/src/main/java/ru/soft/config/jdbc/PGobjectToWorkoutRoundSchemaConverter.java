package ru.soft.config.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

@ReadingConverter
public class PGobjectToWorkoutRoundSchemaConverter implements Converter<PGobject, WorkoutRoundSchemaSnapshot> {

    private final ObjectMapper mapper;

    public PGobjectToWorkoutRoundSchemaConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @SneakyThrows
    @Override
    public WorkoutRoundSchemaSnapshot convert(@NonNull PGobject source) {
        String workoutRoundSchemaJson = source.getValue();
        return this.mapper.readValue(workoutRoundSchemaJson, WorkoutRoundSchemaSnapshot.class);
    }
}