package ru.soft.utils;

import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.*;

import java.sql.SQLException;
import java.util.List;

public final class JsonTestUtils {

    public static final String WORKOUT_ROUND_SCHEMA_JSON =
            "{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"station title_1\",\"description\":\"station description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"station title_2\",\"description\":\"station description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"station title_3\",\"description\":\"station description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]}}";
    public static final String WORKOUT_SCHEMA_JSON =
            "{\"workoutSchema\":{\"rounds\":[{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"station title_1\",\"description\":\"station description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"station title_2\",\"description\":\"station description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"station title_3\",\"description\":\"station description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]},\"title\":\"round title_1\",\"description\":\"round description_1\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"station title_1\",\"description\":\"station description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"station title_2\",\"description\":\"station description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"station title_3\",\"description\":\"station description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]},\"title\":\"round title_2\",\"description\":\"round description_2\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"station title_1\",\"description\":\"station description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"station title_2\",\"description\":\"station description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"station title_3\",\"description\":\"station description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]},\"title\":\"round title_3\",\"description\":\"round description_3\"}]}}";
    public static final String WORKOUT_PLAN_JSON =
            "{\"workoutPlan\":{\"title\":\"workout title\",\"description\":\"workout description\",\"workoutSchema\":{\"rounds\":[{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"station title_1\",\"description\":\"station description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"station title_2\",\"description\":\"station description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"station title_3\",\"description\":\"station description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]},\"title\":\"round title_1\",\"description\":\"round description_1\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"station title_1\",\"description\":\"station description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"station title_2\",\"description\":\"station description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"station title_3\",\"description\":\"station description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]},\"title\":\"round title_2\",\"description\":\"round description_2\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"station title_1\",\"description\":\"station description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"station title_2\",\"description\":\"station description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"station title_3\",\"description\":\"station description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]},\"title\":\"round title_3\",\"description\":\"round description_3\"}]}}}";

    private JsonTestUtils() {
    }

    public static PGobject createWorkoutPlanPGobject() throws SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(WORKOUT_PLAN_JSON);
        return pGobject;
    }

    public static WorkoutPlanSnapshot createWorkoutPlanSnapshot() {
        return WorkoutPlanSnapshot.builder()
                .workoutSchemaSnapshot(createWorkoutSchemaSnapshot())
                .title("workout title")
                .description("workout description")
                .build();
    }

    public static PGobject createWorkoutSchemaPGobject() throws SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(WORKOUT_SCHEMA_JSON);
        return pGobject;
    }

    public static WorkoutSchemaSnapshot createWorkoutSchemaSnapshot() {
        return new WorkoutSchemaSnapshot(List.of(
                WorkoutRoundSnapshot.builder()
                        .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                        .title("round title_1")
                        .description("round description_1")
                        .build(),
                WorkoutRoundSnapshot.builder()
                        .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                        .title("round title_2")
                        .description("round description_2")
                        .build(),
                WorkoutRoundSnapshot.builder()
                        .workoutRoundSchemaSnapshot(createWorkoutRoundSchemaSnapshot())
                        .title("round title_3")
                        .description("round description_3")
                        .build()
        ));
    }

    public static PGobject createWorkoutRoundSchemaPGobject() throws SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(WORKOUT_ROUND_SCHEMA_JSON);
        return pGobject;
    }

    public static WorkoutRoundSchemaSnapshot createWorkoutRoundSchemaSnapshot() {
        return new WorkoutRoundSchemaSnapshot(List.of(
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("station title_1", "station description_1", 1),
                        3, 0, 100, 20, 1),
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("station title_2", "station description_2", 2),
                        6, 10, 0, 10, 2
                ),
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("station title_3", "station description_3", 3),
                        1, 5, 10, 100, 0
                )
        ));
    }
}
