package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import ru.soft.data.config.converter.CustomJsonObjectMapper;

import java.util.concurrent.Callable;

abstract class BasePGobjectToEntityReadingConverterTest<T, C extends Converter<PGobject, T>> {

    private final ObjectMapper jsonMapper = CustomJsonObjectMapper.instance();

    private C readingConverter;

    protected abstract C readingConverter(ObjectMapper mapper);

    protected abstract T expected();

    @BeforeEach
    void init() {
        this.readingConverter = readingConverter(CustomJsonObjectMapper.instance());
    }

    @Test
    void convert() {
        PGobject pGobject = PGobject();
        T actual = this.readingConverter.convert(pGobject);
        T expected = expected();
        Assertions.assertEquals(expected, actual);
    }

    public PGobject PGobject() {
        return wrapper(
                () -> {
                    PGobject pGobject = new PGobject();
                    pGobject.setType("jsonb");
                    String json = this.jsonMapper.writeValueAsString(expected());
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