package ru.soft.data.config.converters.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

@WritingConverter
abstract class ObjectToPGobjectWritingConverter<T> implements Converter<T, PGobject> {

    protected abstract ObjectMapper getMapper();

    @SneakyThrows
    @Override
    public PGobject convert(@NonNull T source) {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(getMapper().writeValueAsString(source));
        return pGobject;
    }
}
