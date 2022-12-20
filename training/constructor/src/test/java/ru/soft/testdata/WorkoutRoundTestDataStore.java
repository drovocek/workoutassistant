package ru.soft.testdata;

import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;
import ru.soft.common.data.snapshot.ExerciseSnapshot;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.common.data.snapshot.WorkoutRoundSnapshot;
import ru.soft.common.data.snapshot.WorkoutStationSnapshot;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.data.model.Exercise;
import ru.soft.data.model.WorkoutRound;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.mapper.WorkoutRoundTOMapper;

import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutRoundTestDataStore implements TestDataStore<WorkoutRound, WorkoutRoundTo> {

    private final static WorkoutRoundTestDataStore INSTANCE = new WorkoutRoundTestDataStore();

    public static WorkoutRoundTestDataStore getInstance() {
        return INSTANCE;
    }

    private final static WorkoutRound ENTITY =
            new WorkoutRound(
                    UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                    workoutRoundSchemaSnapshot()
                    , "Warm up", "Warm up");

    private final TOMapper<WorkoutRound, WorkoutRoundTo> toMapper;

    private WorkoutRoundTestDataStore() {
        this.toMapper = new WorkoutRoundTOMapper();
    }

    @Override
    public WorkoutRound entity() {
        return ENTITY;
    }

    @Override
    public List<WorkoutRound> entities() {
        return List.of(
                entity(),
                new WorkoutRound(UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                        workoutRoundSchemaSnapshot()
                        , "Strength", "For strength"),
                new WorkoutRound(UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                        workoutRoundSchemaSnapshot()
                        , "Endurance", "For endurance")
        );
    }

    @Override
    public WorkoutRoundTo to() {
        return this.toMapper.toTo(ENTITY);
    }

    @Override
    public List<WorkoutRoundTo> tos() {
        return entities().stream()
                .map(this.toMapper::toTo)
                .toList();
    }

    public static WorkoutRoundSnapshot workoutRoundSnapshot() {
        return new WorkoutRoundSnapshot(
                workoutRoundSchemaSnapshot(),
                ENTITY.title(),
                ENTITY.description()
        );
    }

    public static WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot() {
        List<Exercise> exercises = ExerciseTestDataStore.getInstance().entities();
        List<ExerciseSnapshot> exerciseSnapshots = exercises.stream()
                .map(exercise -> new ExerciseSnapshot(exercise.title(), exercise.description(), exercise.complexity()))
                .toList();
        List<WorkoutStationSnapshot> workoutStationSnapshots = exerciseSnapshots.stream()
                .map(exerciseSnapshot ->
                        new WorkoutStationSnapshot(
                                exerciseSnapshot,
                                3, 0, 100, 20, 1)
                )
                .toList();
        return new WorkoutRoundSchemaSnapshot(workoutStationSnapshots);
    }

    public static final String WORKOUT_ROUND_SCHEMA_JSON =
          "{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]}}";

    public static PGobject workoutRoundSchemaPGobject() {
        return TestDataStore.wrapper(
                () -> {
                    PGobject pGobject = new PGobject();
                    pGobject.setType("jsonb");
                    String json = jsonMapper.writeValueAsString(workoutRoundSchemaSnapshot());
                    TestDataStore.throwIfNotEquals(json, WORKOUT_ROUND_SCHEMA_JSON);
                    pGobject.setValue(json);
                    return pGobject;
                });
    }
}
