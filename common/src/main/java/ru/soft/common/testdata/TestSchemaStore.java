package ru.soft.common.testdata;

import lombok.experimental.UtilityClass;
import ru.soft.common.data.elements.DurationUnit;
import ru.soft.common.data.elements.Rest;
import ru.soft.common.data.elements.Station;
import ru.soft.common.data.elements.WorkoutSchema;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;

import java.util.List;

@UtilityClass
public class TestSchemaStore {

    public static WorkoutSchema workoutSchema() {
        return new WorkoutSchema(
                List.of(
                        Station.builder()
                                .exercise(ExerciseToTestDataStore.example(false))
                                .weight(50)
                                .repetitions(12)
                                .duration(0)
                                .unit(DurationUnit.SEC)
                                .order(1)
                                .build(),
                        Rest.builder()
                                .duration(60)
                                .unit(DurationUnit.SEC)
                                .order(2)
                                .build(),
                        Station.builder()
                                .exercise(ExerciseToTestDataStore.example(false))
                                .weight(60)
                                .repetitions(10)
                                .duration(0)
                                .unit(DurationUnit.SEC)
                                .order(3)
                                .build(),
                        Rest.builder()
                                .duration(2)
                                .unit(DurationUnit.MIN)
                                .order(4)
                                .build(),
                        Station.builder()
                                .exercise(ExerciseToTestDataStore.example(false))
                                .weight(70)
                                .repetitions(8)
                                .duration(0)
                                .unit(DurationUnit.SEC)
                                .order(5)
                                .build(),
                        Rest.builder()
                                .duration(1)
                                .unit(DurationUnit.MIN)
                                .order(6)
                                .build()
                )
        );
    }
}
