package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import ru.soft.data.config.converter.CustomJsonObjectMapper;

import java.sql.SQLException;

abstract class BaseEntityToPGobjectWritingConverterTest<T, C extends Converter<T, PGobject>> {

    private C writingConverter;

    protected abstract C writingConverter(ObjectMapper mapper);

    protected abstract T forWriting();

    protected abstract PGobject expected();

    @BeforeEach
    void init() {
        this.writingConverter = writingConverter(CustomJsonObjectMapper.instance());
    }

    @Test
    void convert() throws SQLException {
        T forWriting = forWriting();
        PGobject actual = this.writingConverter.convert(forWriting);
        PGobject expected = expected();
        Assertions.assertEquals(expected, actual);
    }
}