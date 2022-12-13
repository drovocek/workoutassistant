package ru.soft.data.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import ru.soft.data.BaseEntity;
import ru.soft.data.config.converters.read.PGobjectToWorkoutPlanSnapshotReadingConverter;
import ru.soft.data.config.converters.read.PGobjectToWorkoutRoundSchemaSnapshotReadingConverter;
import ru.soft.data.config.converters.read.PGobjectToWorkoutSchemaSnapshotReadingConverter;
import ru.soft.data.config.converters.write.WorkoutPlanSnapshotToPGobjectWritingConverter;
import ru.soft.data.config.converters.write.WorkoutRoundSchemaSnapshotToPGobjectWritingConverter;
import ru.soft.data.config.converters.write.WorkoutSchemaSnapshotToPGobjectWritingConverter;

import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    public static ObjectMapper jdbcObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        mapper.setAccessorNaming(new DefaultAccessorNamingStrategy.Provider().withGetterPrefix("").withSetterPrefix(""));
        return mapper;
    }

    @Bean
    BeforeConvertCallback<BaseEntity> prepareForConvert() {
        return entity -> {
            if (entity.id() == null) {
                return newWithId(entity);
            }
            return entity;
        };
    }

    private BaseEntity newWithId(BaseEntity entity) {
        UUID id = UUID.randomUUID();
        return entity.newWithId(id);
    }

    @Override
    protected List<?> userConverters() {
        ObjectMapper objectMapper = jdbcObjectMapper();
        return List.of(
                new WorkoutRoundSchemaSnapshotToPGobjectWritingConverter(objectMapper),
                new PGobjectToWorkoutRoundSchemaSnapshotReadingConverter(objectMapper),
                new WorkoutSchemaSnapshotToPGobjectWritingConverter(objectMapper),
                new PGobjectToWorkoutSchemaSnapshotReadingConverter(objectMapper),
                new WorkoutPlanSnapshotToPGobjectWritingConverter(objectMapper),
                new PGobjectToWorkoutPlanSnapshotReadingConverter(objectMapper)
        );
    }
}
