package ru.soft.config.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

@WritingConverter
public class WorkoutSchemaSnapshotToPGobjectConverter implements Converter<WorkoutSchemaSnapshot, PGobject> {

    private final ObjectMapper mapper;

    public WorkoutSchemaSnapshotToPGobjectConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @SneakyThrows
    @Override
    public PGobject convert(@NonNull WorkoutSchemaSnapshot source) {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(mapper.writeValueAsString(source));
        return pGobject;
    }
}