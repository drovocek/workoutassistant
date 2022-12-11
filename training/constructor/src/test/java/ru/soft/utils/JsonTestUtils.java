package ru.soft.utils;

import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.ExerciseSnapshot;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.data.model.snapshot.WorkoutStationSnapshot;

import java.sql.SQLException;
import java.util.List;

public final class JsonTestUtils {

    public static final String WORKOUT_ROUND_SCHEMA_JSON =
            "{\"stations\":[{\"exercise\":{\"title\":\"title_1\",\"description\":\"description_1\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"title_2\",\"description\":\"description_2\",\"complexity\":2},\"repetitions\":6,\"weight\":10,\"duration\":0,\"rest\":10,\"order\":2},{\"exercise\":{\"title\":\"title_3\",\"description\":\"description_3\",\"complexity\":3},\"repetitions\":1,\"weight\":5,\"duration\":10,\"rest\":100,\"order\":0}]}";

    private JsonTestUtils() {
    }

    public static PGobject createWorkoutRoundSchemaPGobject() throws SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(WORKOUT_ROUND_SCHEMA_JSON);
        return pGobject;
    }

    public static WorkoutRoundSchemaSnapshot createWorkoutRoundSchema() {
        return new WorkoutRoundSchemaSnapshot(List.of(
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("title_1", "description_1", 1),
                        3, 0, 100, 20, 1),
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("title_2", "description_2", 2),
                        6, 10, 0, 10, 2
                ),
                new WorkoutStationSnapshot(
                        new ExerciseSnapshot("title_3", "description_3", 3),
                        1, 5, 10, 100, 0
                )
        ));
    }
}
