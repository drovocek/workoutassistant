package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;

@Getter
@RequiredArgsConstructor
public class PGobjectToWorkoutRoundSchemaSnapshotReadingConverter extends PGobjectToObjectReadingConverter<WorkoutRoundSchemaSnapshot> {

    private final ObjectMapper mapper;

    @Override
    protected Class<WorkoutRoundSchemaSnapshot> getResultClass() {
        return WorkoutRoundSchemaSnapshot.class;
    }
}