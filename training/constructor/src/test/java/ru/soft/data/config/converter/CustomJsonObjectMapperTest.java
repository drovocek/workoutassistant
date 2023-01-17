package ru.soft.data.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.soft.common.testdata.TestSchemaStore;

class CustomJsonObjectMapperTest {

    private final ObjectMapper jsonMapper = CustomJsonObjectMapper.instance();

    private static final String WORKOUT_SCHEMA_JSON =
            "{\"workoutElements\":[{\"type\":\"station\",\"title\":\"Exercise one title\",\"note\":\"Exercise one note\",\"exerciseId\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":12,\"weight\":50,\"duration\":0,\"unit\":\"SEC\",\"order\":1},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":60,\"unit\":\"SEC\",\"order\":2},{\"type\":\"station\",\"title\":\"Exercise two title\",\"note\":\"Exercise two note\",\"exerciseId\":\"ff252f6e-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":10,\"weight\":60,\"duration\":0,\"unit\":\"SEC\",\"order\":3},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":2,\"unit\":\"MIN\",\"order\":4},{\"type\":\"station\",\"title\":\"Exercise tree title\",\"note\":\"Exercise tree note\",\"exerciseId\":\"05bf5a98-6e5e-11ed-a1eb-0242ac120002\",\"repetitions\":8,\"weight\":70,\"duration\":0,\"unit\":\"SEC\",\"order\":5},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":1,\"unit\":\"HOUR\",\"order\":6}]}";

    @Test
    void testJsonFormat() throws JsonProcessingException {
        String workoutSchemaJson = jsonMapper.writeValueAsString(TestSchemaStore.workoutSchema());
        Assertions.assertEquals(WORKOUT_SCHEMA_JSON, workoutSchemaJson);
    }
}