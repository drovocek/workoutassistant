package ru.soft.web.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.soft.data.HasId;
import ru.soft.web.exception.IllegalRequestDataException;

import java.util.UUID;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (bean.id() != null) {
            throw new IllegalRequestDataException("%s must be new (id==null)".formatted(bean.getClass().getSimpleName()));
        }
    }

    public static void checkNotNew(HasId bean) {
        if (bean.id() == null) {
            throw new IllegalRequestDataException("%s must be not new (id!=null)".formatted(bean.getClass().getSimpleName()));
        }
    }

    public static void checkModification(int count, UUID id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=%s not found".formatted(id));
        }
    }

    public static <T> T checkExisted(T obj, UUID id) {
        if (obj == null) {
            throw new IllegalRequestDataException("Entity with id=%s not found".formatted(id));
        }
        return obj;
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}