package ru.soft;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainersConfig {

    public static class CustomPostgreSQLContainer extends PostgreSQLContainer<CustomPostgreSQLContainer> {
        private static final String IMAGE_VERSION = "postgres:13";
        private static CustomPostgreSQLContainer container;

        public CustomPostgreSQLContainer() {
            super(IMAGE_VERSION);
        }

        public static CustomPostgreSQLContainer getInstance() {
            if (container == null) {
                container = new CustomPostgreSQLContainer();
            }
            return container;
        }
    }
}
