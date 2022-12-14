package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import ru.soft.data.config.converter.CustomJsonObjectMapper;

abstract class BasePGobjectToEntityReadingConverterTest<T, C extends Converter<PGobject, T>> {

    private C readingConverter;

    protected abstract C readingConverter(ObjectMapper mapper);

    protected abstract PGobject forReading();

    protected abstract T expected();

    @BeforeEach
    void init() {
        this.readingConverter = readingConverter(CustomJsonObjectMapper.instance());
    }

    @Test
    void convert() {
        PGobject pGobject = forReading();
        T actual = this.readingConverter.convert(pGobject);
        T expected = expected();
        Assertions.assertEquals(expected, actual);
    }
}