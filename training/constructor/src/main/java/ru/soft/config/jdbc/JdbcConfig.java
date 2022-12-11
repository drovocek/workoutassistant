package ru.soft.config.jdbc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import ru.soft.data.BaseEntity;
import ru.soft.data.BaseEntityWithComplexity;

import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    public static ObjectMapper jdbcObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setAccessorNaming(new DefaultAccessorNamingStrategy.Provider().withGetterPrefix("").withSetterPrefix(""));
        return mapper;
    }

    @Bean
    BeforeConvertCallback<BaseEntity> prepareForConvert() {
        return this::prepareForConvert;
    }

    private BaseEntity prepareForConvert(BaseEntity entity) {
        boolean hasComplexity = entity instanceof BaseEntityWithComplexity;
        if (entity.getId() == null) {
            log.info("newWithId id for entity {}", entity);
            UUID id = UUID.randomUUID();
            return hasComplexity ?
                    ((BaseEntityWithComplexity) entity).newWithActualComplexity(id) :
                    entity.newWithId(id);
        }
        return hasComplexity ?
                ((BaseEntityWithComplexity) entity).copyWithActualComplexity() :
                entity;
    }

    @Override
    protected List<?> userConverters() {
        return List.of(
                new WorkoutRoundSchemaToPGobjectConverter(jdbcObjectMapper()),
                new PGobjectToWorkoutRoundSchemaConverter(jdbcObjectMapper()));
    }
}
