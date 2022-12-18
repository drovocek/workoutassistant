package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

@Getter
@RequiredArgsConstructor
public class PGobjectToWorkoutPlanSnapshotReadingConverter extends PGobjectToObjectReadingConverter<WorkoutPlanSnapshot> {

    private final ObjectMapper mapper;

    @Override
    protected Class<WorkoutPlanSnapshot> getResultClass() {
        return WorkoutPlanSnapshot.class;
    }
}