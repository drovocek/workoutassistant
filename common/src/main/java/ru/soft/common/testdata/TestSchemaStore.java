package ru.soft.common.testdata;

import lombok.experimental.UtilityClass;
import ru.soft.common.data.elements.DurationUnit;
import ru.soft.common.data.elements.Rest;
import ru.soft.common.data.elements.Station;
import ru.soft.common.data.elements.WorkoutSchema;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class TestSchemaStore {

    public static WorkoutSchema workoutSchema() {
        return new WorkoutSchema(
                List.of(
                        Station.builder()
                                .title("Exercise one title")
                                .note("Exercise one note")
                                .exerciseId(UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"))
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
                                .title("Exercise two title")
                                .note("Exercise two note")
                                .exerciseId(UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"))
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
                                .title("Exercise tree title")
                                .note("Exercise tree note")
                                .exerciseId(UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"))
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
