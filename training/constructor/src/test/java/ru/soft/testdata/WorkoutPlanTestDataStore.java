package ru.soft.testdata;

import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;
import ru.soft.common.data.snapshot.WorkoutRoundSnapshot;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.data.model.WorkoutRound;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.mapper.WorkoutPlanTOMapper;

import java.util.List;
import java.util.UUID;

@Component
public final class WorkoutPlanTestDataStore implements TestDataStore<WorkoutPlan, WorkoutPlanTo> {

    private final static WorkoutPlanTestDataStore INSTANCE = new WorkoutPlanTestDataStore();

    public static WorkoutPlanTestDataStore getInstance() {
        return INSTANCE;
    }

    private final static WorkoutPlan ENTITY =
            new WorkoutPlan(
                    UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                    workoutSchemaSnapshot()
                    , "Push-up training",
                    "Push-up description");

    private final TOMapper<WorkoutPlan, WorkoutPlanTo> toMapper;

    private WorkoutPlanTestDataStore() {
        this.toMapper = new WorkoutPlanTOMapper();
    }

    @Override
    public WorkoutPlan entity() {
        return ENTITY;
    }

    @Override
    public List<WorkoutPlan> entities() {
        return List.of(
                entity(),
                new WorkoutPlan(UUID.fromString("612ccb86-7a52-11ed-a1eb-0242ac120002"),
                        workoutSchemaSnapshot()
                        , "Barbell squats training", "Barbell squats description"),
                new WorkoutPlan(UUID.fromString("65d1de6a-7a52-11ed-a1eb-0242ac120002"),
                        workoutSchemaSnapshot()
                        , "Pull-ups training", "Pull-ups description")
        );
    }

    @Override
    public WorkoutPlanTo to() {
        return this.toMapper.toTo(ENTITY);
    }

    @Override
    public List<WorkoutPlanTo> tos() {
        return entities().stream()
                .map(this.toMapper::toTo)
                .toList();
    }

    public static WorkoutPlanSnapshot workoutPlanSnapshot() {
        return new WorkoutPlanSnapshot(
                workoutSchemaSnapshot(),
                ENTITY.title(),
                ENTITY.description()
        );
    }

    public static WorkoutSchemaSnapshot workoutSchemaSnapshot() {
        List<WorkoutRound> rounds = WorkoutRoundTestDataStore.getInstance().entities();
        List<WorkoutRoundSnapshot> workoutRoundSnapshots = rounds.stream()
                .map(round ->
                        WorkoutRoundSnapshot.builder()
                                .workoutRoundSchemaSnapshot(WorkoutRoundTestDataStore.workoutRoundSchemaSnapshot())
                                .title(round.title())
                                .description(round.description())
                                .build())
                .toList();
        return new WorkoutSchemaSnapshot(workoutRoundSnapshots);
    }

    public static final String WORKOUT_PLAN_JSON =
            "{\"workoutPlan\":{\"title\":\"Push-up training\",\"description\":\"Push-up description\",\"workoutSchema\":{\"rounds\":[{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Warm up\",\"description\":\"Warm up\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Strength\",\"description\":\"For strength\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Endurance\",\"description\":\"For endurance\"}]}}}";

    public static PGobject workoutPlanPGobject() {
        return TestDataStore.wrapper(
                () -> {
                    PGobject pGobject = new PGobject();
                    pGobject.setType("jsonb");
                    String json = jsonMapper.writeValueAsString(workoutPlanSnapshot());
                    TestDataStore.throwIfNotEquals(json, WORKOUT_PLAN_JSON);
                    pGobject.setValue(json);
                    return pGobject;
                }
        );
    }

    public static final String WORKOUT_SCHEMA_JSON =
            "{\"workoutSchema\":{\"rounds\":[{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Warm up\",\"description\":\"Warm up\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Strength\",\"description\":\"For strength\"},{\"roundSchema\":{\"roundStations\":[{\"exercise\":{\"title\":\"Push-up title\",\"description\":\"Push-up description\",\"complexity\":1},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Barbell squat title\",\"description\":\"Barbell squat description\",\"complexity\":2},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1},{\"exercise\":{\"title\":\"Pull-up title\",\"description\":\"Pull-up description\",\"complexity\":3},\"repetitions\":3,\"weight\":0,\"duration\":100,\"rest\":20,\"order\":1}]},\"title\":\"Endurance\",\"description\":\"For endurance\"}]}}";

    public static PGobject workoutSchemaPGobject() {
        return TestDataStore.wrapper(
                () -> {
                    PGobject pGobject = new PGobject();
                    pGobject.setType("jsonb");
                    String json = jsonMapper.writeValueAsString(workoutSchemaSnapshot());
                    TestDataStore.throwIfNotEquals(json, WORKOUT_SCHEMA_JSON);
                    pGobject.setValue(json);
                    return pGobject;
                });
    }
}
