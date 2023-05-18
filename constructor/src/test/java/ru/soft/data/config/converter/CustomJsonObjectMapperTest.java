package ru.soft.data.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.soft.common.data.elements.WorkoutSchema;
import ru.soft.common.testdata.TestSchemaStore;
import ru.soft.common.testdata.to.RoundToTestDataStore;
import ru.soft.common.testdata.to.WorkoutToTestDataStore;
import ru.soft.common.to.RoundTo;
import ru.soft.common.to.WorkoutTo;

class CustomJsonObjectMapperTest {

    private final ObjectMapper jsonMapper = CustomJsonObjectMapper.instance();

    private static final String WORKOUT_SCHEMA_JSON =
            "{\"workoutElements\":[{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":1,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":12,\"weight\":50,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":2,\"duration\":60,\"unit\":\"SEC\"},{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":3,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":10,\"weight\":60,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":4,\"duration\":2,\"unit\":\"MIN\"},{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":5,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":8,\"weight\":70,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":6,\"duration\":1,\"unit\":\"MIN\"}]}";

    private static final String ROUND_JSON =
            "{\"type\":\"round\",\"title\":\"Push-up plan title\",\"note\":\"Push-up plan note\",\"order\":0,\"id\":\"5c83571c-7a52-11ed-a1eb-0242ac120002\",\"workoutSchema\":{\"workoutElements\":[{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":1,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":12,\"weight\":50,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":2,\"duration\":60,\"unit\":\"SEC\"},{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":3,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":10,\"weight\":60,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":4,\"duration\":2,\"unit\":\"MIN\"},{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":5,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":8,\"weight\":70,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":6,\"duration\":1,\"unit\":\"MIN\"}]}}";

    private static final String TRAINING_SESSION_JSON =
            "{\"id\":\"a34798c2-7ac8-11ed-a1eb-0242ac120002\",\"workoutSchema\":{\"workoutElements\":[{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":1,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":12,\"weight\":50,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":2,\"duration\":60,\"unit\":\"SEC\"},{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":3,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":10,\"weight\":60,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":4,\"duration\":2,\"unit\":\"MIN\"},{\"type\":\"station\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\",\"order\":5,\"exercise\":{\"id\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"title\":\"Push-up exercise title\",\"note\":\"Push-up exercise note\"},\"repetitions\":8,\"weight\":70,\"duration\":0,\"unit\":\"SEC\"},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"order\":6,\"duration\":1,\"unit\":\"MIN\"}]},\"dateTime\":[2024,3,20,19,10,25],\"title\":\"Easy session title\",\"note\":\"Easy session note\"}";

    @Test
    void testJsonFormat() throws JsonProcessingException {
        String workoutSchemaJson = jsonMapper.writeValueAsString(TestSchemaStore.workoutSchema());
        Assertions.assertEquals(WORKOUT_SCHEMA_JSON, workoutSchemaJson);

        WorkoutSchema actualWorkoutSchema = jsonMapper.readValue(workoutSchemaJson, WorkoutSchema.class);
        Assertions.assertEquals(TestSchemaStore.workoutSchema(), actualWorkoutSchema);

        RoundTo workout = RoundToTestDataStore.example(false);
        String workoutJson = jsonMapper.writeValueAsString(workout);
        Assertions.assertEquals(ROUND_JSON, workoutJson);

        RoundTo actualWorkout = jsonMapper.readValue(workoutJson, RoundTo.class);
        Assertions.assertEquals(workout, actualWorkout);

        WorkoutTo trainingSession = WorkoutToTestDataStore.example(false);
        String trainingSessionJson = jsonMapper.writeValueAsString(trainingSession);
        Assertions.assertEquals(TRAINING_SESSION_JSON, trainingSessionJson);

        WorkoutTo actualTrainingSession = jsonMapper.readValue(trainingSessionJson, WorkoutTo.class);
        Assertions.assertEquals(trainingSession, actualTrainingSession);
    }
}