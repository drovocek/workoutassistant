package ru.soft.data.config.converters.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.data.model.snapshot.WorkoutPlanSnapshot;

@Getter
@RequiredArgsConstructor
public class PGobjectToWorkoutPlanSnapshotReadingConverter extends PGobjectToObjectReadingConverter<WorkoutPlanSnapshot> {

    private final ObjectMapper mapper;

    @Override
    protected Class<WorkoutPlanSnapshot> getResultClass() {
        return WorkoutPlanSnapshot.class;
    }
}