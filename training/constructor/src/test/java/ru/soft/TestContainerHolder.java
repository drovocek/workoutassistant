package ru.soft;

import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Sql({"/db/schema.sql"})
public abstract class TestContainerHolder {

    @Container
    private final PostgreSQLContainer<?> topLevelContainer = TestContainerConfig.getInstance();
}