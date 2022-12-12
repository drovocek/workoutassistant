package ru.soft.config.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

@ReadingConverter
public class PGobjectToWorkoutSchemaSnapshotConverter implements Converter<PGobject, WorkoutSchemaSnapshot> {

    private final ObjectMapper mapper;

    public PGobjectToWorkoutSchemaSnapshotConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @SneakyThrows
    @Override
    public WorkoutSchemaSnapshot convert(@NonNull PGobject source) {
        String workoutRoundSchemaJson = source.getValue();
        return this.mapper.readValue(workoutRoundSchemaJson, WorkoutSchemaSnapshot.class);
    }
}