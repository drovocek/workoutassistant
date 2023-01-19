package ru.soft.data.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.soft.common.data.elements.WorkoutSchema;
import ru.soft.common.testdata.TestSchemaStore;
import ru.soft.common.testdata.to.TrainingSessionToTestDataStore;
import ru.soft.common.testdata.to.WorkoutToTestDataStore;
import ru.soft.common.to.TrainingSessionTo;
import ru.soft.common.to.WorkoutTo;

class CustomJsonObjectMapperTest {

    private final ObjectMapper jsonMapper = CustomJsonObjectMapper.instance();

    private static final String WORKOUT_SCHEMA_JSON =
            "{\"workoutElements\":[{\"type\":\"station\",\"title\":\"Exercise one title\",\"note\":\"Exercise one note\",\"exerciseId\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":12,\"weight\":50,\"duration\":0,\"unit\":\"SEC\",\"order\":1},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":60,\"unit\":\"SEC\",\"order\":2},{\"type\":\"station\",\"title\":\"Exercise two title\",\"note\":\"Exercise two note\",\"exerciseId\":\"ff252f6e-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":10,\"weight\":60,\"duration\":0,\"unit\":\"SEC\",\"order\":3},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":2,\"unit\":\"MIN\",\"order\":4},{\"type\":\"station\",\"title\":\"Exercise tree title\",\"note\":\"Exercise tree note\",\"exerciseId\":\"05bf5a98-6e5e-11ed-a1eb-0242ac120002\",\"repetitions\":8,\"weight\":70,\"duration\":0,\"unit\":\"SEC\",\"order\":5},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":1,\"unit\":\"HOUR\",\"order\":6}]}";

    private static final String TRAINING_SESSION_JSON =
            "{\"id\":\"a34798c2-7ac8-11ed-a1eb-0242ac120002\",\"workoutSchema\":{\"workoutElements\":[{\"type\":\"station\",\"title\":\"Exercise one title\",\"note\":\"Exercise one note\",\"exerciseId\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":12,\"weight\":50,\"duration\":0,\"unit\":\"SEC\",\"order\":1},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":60,\"unit\":\"SEC\",\"order\":2},{\"type\":\"station\",\"title\":\"Exercise two title\",\"note\":\"Exercise two note\",\"exerciseId\":\"ff252f6e-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":10,\"weight\":60,\"duration\":0,\"unit\":\"SEC\",\"order\":3},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":2,\"unit\":\"MIN\",\"order\":4},{\"type\":\"station\",\"title\":\"Exercise tree title\",\"note\":\"Exercise tree note\",\"exerciseId\":\"05bf5a98-6e5e-11ed-a1eb-0242ac120002\",\"repetitions\":8,\"weight\":70,\"duration\":0,\"unit\":\"SEC\",\"order\":5},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":1,\"unit\":\"HOUR\",\"order\":6}]},\"dateTime\":[2024,3,20,19,10,25],\"title\":\"Easy session title\",\"note\":\"Easy session note\"}";

    private static final String WORKOUT_JSON =
           "{\"id\":\"5c83571c-7a52-11ed-a1eb-0242ac120002\",\"workoutSchema\":{\"workoutElements\":[{\"type\":\"station\",\"title\":\"Exercise one title\",\"note\":\"Exercise one note\",\"exerciseId\":\"f386d086-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":12,\"weight\":50,\"duration\":0,\"unit\":\"SEC\",\"order\":1},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":60,\"unit\":\"SEC\",\"order\":2},{\"type\":\"station\",\"title\":\"Exercise two title\",\"note\":\"Exercise two note\",\"exerciseId\":\"ff252f6e-6e5d-11ed-a1eb-0242ac120002\",\"repetitions\":10,\"weight\":60,\"duration\":0,\"unit\":\"SEC\",\"order\":3},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":2,\"unit\":\"MIN\",\"order\":4},{\"type\":\"station\",\"title\":\"Exercise tree title\",\"note\":\"Exercise tree note\",\"exerciseId\":\"05bf5a98-6e5e-11ed-a1eb-0242ac120002\",\"repetitions\":8,\"weight\":70,\"duration\":0,\"unit\":\"SEC\",\"order\":5},{\"type\":\"rest\",\"title\":\"rest\",\"note\":\"rest between sets\",\"duration\":1,\"unit\":\"HOUR\",\"order\":6}]},\"title\":\"Push-up plan title\",\"note\":\"Push-up plan note\"}";
    @Test
    void testJsonFormat() throws JsonProcessingException {
        String workoutSchemaJson = jsonMapper.writeValueAsString(TestSchemaStore.workoutSchema());
        Assertions.assertEquals(WORKOUT_SCHEMA_JSON, workoutSchemaJson);

        WorkoutSchema actualWorkoutSchema = jsonMapper.readValue(workoutSchemaJson, WorkoutSchema.class);
        Assertions.assertEquals(TestSchemaStore.workoutSchema(), actualWorkoutSchema);

        WorkoutTo workout = WorkoutToTestDataStore.example(false);
        String workoutJson = jsonMapper.writeValueAsString(workout);
        Assertions.assertEquals(WORKOUT_JSON, workoutJson);

        WorkoutTo actualWorkout = jsonMapper.readValue(workoutJson, WorkoutTo.class);
        Assertions.assertEquals(workout, actualWorkout);

        TrainingSessionTo trainingSession = TrainingSessionToTestDataStore.example(false);
        String trainingSessionJson = jsonMapper.writeValueAsString(trainingSession);
        Assertions.assertEquals(TRAINING_SESSION_JSON, trainingSessionJson);

        TrainingSessionTo actualTrainingSession = jsonMapper.readValue(trainingSessionJson, TrainingSessionTo.class);
        Assertions.assertEquals(trainingSession, actualTrainingSession);
    }
}