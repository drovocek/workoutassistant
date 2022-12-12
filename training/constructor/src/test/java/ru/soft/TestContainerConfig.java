package ru.soft;

import org.testcontainers.containers.PostgreSQLContainer;

//https://www.testcontainers.org/test_framework_integration/junit_5/
//https://www.baeldung.com/spring-boot-testcontainers-integration-test
public class TestContainerConfig extends PostgreSQLContainer<TestContainerConfig> {

    private static final String IMAGE_VERSION = "postgres:13";
    private static TestContainerConfig CONTAINER;

    private TestContainerConfig() {
        super(IMAGE_VERSION);
    }

    public static TestContainerConfig getInstance() {
        if (CONTAINER == null) {
            CONTAINER = new TestContainerConfig();
        }
        return CONTAINER;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", CONTAINER.getJdbcUrl() + "&stringtype=unspecified");
        System.setProperty("DB_USERNAME", CONTAINER.getUsername());
        System.setProperty("DB_PASSWORD", CONTAINER.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}