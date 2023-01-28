package ru.soft.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import ru.soft.common.data.HasId;
import ru.soft.web.exception.IllegalRequestDataException;

@UtilityClass
public class ValidationUtil {

    public static final String ENTITY_NOT_FOUND_TEMPLATE = "Entity with id=%s not found";

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

    public static <ID> void checkModification(int count, ID id) {
        if (count == 0) {
            throw new IllegalRequestDataException(ENTITY_NOT_FOUND_TEMPLATE.formatted(id), HttpStatus.NOT_FOUND);
        }
    }

    public static <T, ID> T checkExisted(T obj, ID id) {
        if (obj == null) {
            throw new IllegalRequestDataException(ENTITY_NOT_FOUND_TEMPLATE.formatted(id), HttpStatus.NOT_FOUND);
        }
        return obj;
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}