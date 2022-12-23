package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import ru.soft.data.config.converter.CustomJsonObjectMapper;

import java.util.concurrent.Callable;

abstract class BaseEntityToPGobjectWritingConverterTest<T, C extends Converter<T, PGobject>> {

    private final ObjectMapper jsonMapper = CustomJsonObjectMapper.instance();

    private C writingConverter;

    protected abstract C writingConverter(ObjectMapper mapper);

    protected abstract T forWriting();

    @BeforeEach
    void init() {
        this.writingConverter = writingConverter(CustomJsonObjectMapper.instance());
    }

    @Test
    void convert() {
        T forWriting = forWriting();
        PGobject actual = this.writingConverter.convert(forWriting);
        PGobject expected = PGobject();
        Assertions.assertEquals(expected, actual);
    }

    public PGobject PGobject() {
        return wrapper(
                () -> {
                    PGobject pGobject = new PGobject();
                    pGobject.setType("jsonb");
                    String json = this.jsonMapper.writeValueAsString(forWriting());
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