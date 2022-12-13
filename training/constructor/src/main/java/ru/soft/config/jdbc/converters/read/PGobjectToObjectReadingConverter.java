package ru.soft.config.jdbc.converters.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

@ReadingConverter
abstract class PGobjectToObjectReadingConverter<T> implements Converter<PGobject, T> {

    protected abstract ObjectMapper getMapper();

    protected abstract Class<T> getResultClass();

    @SneakyThrows
    @Override
    public T convert(@NonNull PGobject source) {
        String workoutRoundSchemaJson = source.getValue();
        return getMapper().readValue(workoutRoundSchemaJson, getResultClass());
    }
}
