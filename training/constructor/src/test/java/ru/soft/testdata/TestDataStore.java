package ru.soft.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.data.config.converter.CustomJsonObjectMapper;

import java.util.List;
import java.util.concurrent.Callable;

public interface TestDataStore<T, TO> {

    ObjectMapper jsonMapper = CustomJsonObjectMapper.instance();

    T entity();

    List<T> entities();

    TO to();

    List<TO> tos();

    static PGobject wrapper(Callable<PGobject> pGobjectSupplier) {
        try {
            return pGobjectSupplier.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void throwIfNotEquals(String first, String second) {
        if (!first.equals(second)) {
            throw new RuntimeException("json не эквивалентны: \n" +
                    first + "\n" +
                    second);
        }
    }
}
