package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ru.soft.common.data.StationsSchema;

@RequiredArgsConstructor
public class PGobjectToStationSchemaReadingConverter implements Converter<PGobject, StationsSchema> {

    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public StationsSchema convert(@NonNull PGobject source) {
        String workoutRoundSchemaJson = source.getValue();
        return this.mapper.readValue(workoutRoundSchemaJson, StationsSchema.class);
    }
}