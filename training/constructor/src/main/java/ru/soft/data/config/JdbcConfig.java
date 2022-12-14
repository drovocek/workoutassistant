package ru.soft.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import ru.soft.data.BaseEntity;
import ru.soft.data.config.converter.CustomJsonObjectMapper;
import ru.soft.data.config.converter.read.PGobjectToWorkoutPlanSnapshotReadingConverter;
import ru.soft.data.config.converter.read.PGobjectToWorkoutRoundSchemaSnapshotReadingConverter;
import ru.soft.data.config.converter.read.PGobjectToWorkoutSchemaSnapshotReadingConverter;
import ru.soft.data.config.converter.write.WorkoutPlanSnapshotToPGobjectWritingConverter;
import ru.soft.data.config.converter.write.WorkoutRoundSchemaSnapshotToPGobjectWritingConverter;
import ru.soft.data.config.converter.write.WorkoutSchemaSnapshotToPGobjectWritingConverter;

import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

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
        ObjectMapper objectMapper = CustomJsonObjectMapper.instance();
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
