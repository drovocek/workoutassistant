package ru.soft.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import ru.soft.data.model.BaseEntity;
import ru.soft.data.config.converter.CustomJsonObjectMapper;
import ru.soft.data.config.converter.read.PGobjectToWorkoutSchemaReadingConverter;
import ru.soft.data.config.converter.write.WorkoutSchemaToPGobjectWritingConverter;

import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Bean
    BeforeConvertCallback<BaseEntity> prepareForConvert() {
        return entity -> {
            if (entity.id() == null) {
                System.out.println(newWithId(entity));
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
        ObjectMapper objectMapper = CustomJsonObjectMapper.instance();
        return List.of(
                new WorkoutSchemaToPGobjectWritingConverter(objectMapper),
                new PGobjectToWorkoutSchemaReadingConverter(objectMapper)
        );
    }
}
