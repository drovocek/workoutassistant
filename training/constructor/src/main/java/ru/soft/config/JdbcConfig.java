package ru.soft.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import ru.soft.data.model.BaseEntity;

import java.util.UUID;

@Slf4j
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Bean
    public BeforeConvertCallback<BaseEntity> idSetting() {
        return entity -> {
            if (entity.getId() == null) {
                return newWithId(entity);
            }
            return entity;
        };
    }

    private BaseEntity newWithId(BaseEntity entity) {
        log.info("newWithId id for entity {}", entity);
        UUID id = UUID.randomUUID();
        return entity.newWithId(id);
    }
}
