package ru.soft.data.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.soft.testdata.snapshot.TestSnapshotStore;

class CustomJsonObjectMapperTest {

    private static final String WORKOUT_ROUND_SCHEMA_JSON =
            "{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]}}";
    private static final String WORKOUT_SCHEMA_JSON =
            "{\"workoutSchema\":{\"rounds\":[{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Warm up\",\"description\":\"Warm up\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Strength\",\"description\":\"For strength\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Endurance\",\"description\":\"For endurance\"}]}}";
    private static final String WORKOUT_PLAN_JSON =
            "{\"workoutPlan\":{\"title\":\"Push-up training\",\"description\":\"Push-up description\",\"workoutSchema\":{\"rounds\":[{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Warm up\",\"description\":\"Warm up\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Strength\",\"description\":\"For strength\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Endurance\",\"description\":\"For endurance\"}]}}}";

    @Test
    void testJsonFormat() throws JsonProcessingException {
        CustomJsonObjectMapper mapper = CustomJsonObjectMapper.instance();

        String workoutRoundSchemaJson = mapper.writeValueAsString(TestSnapshotStore.workoutRoundSchemaSnapshot());
        Assertions.assertEquals(WORKOUT_ROUND_SCHEMA_JSON, workoutRoundSchemaJson);

        String workoutSchemaJson = mapper.writeValueAsString(TestSnapshotStore.workoutSchemaSnapshot());
        Assertions.assertEquals(WORKOUT_SCHEMA_JSON, workoutSchemaJson);

        String workoutPlanJson = mapper.writeValueAsString(TestSnapshotStore.workoutPlanSnapshot());
        Assertions.assertEquals(WORKOUT_PLAN_JSON, workoutPlanJson);
    }
}