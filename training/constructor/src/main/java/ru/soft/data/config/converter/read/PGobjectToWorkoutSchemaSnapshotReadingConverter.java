package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

@Getter
@RequiredArgsConstructor
public class PGobjectToWorkoutSchemaSnapshotReadingConverter extends PGobjectToObjectReadingConverter<WorkoutSchemaSnapshot> {

    private final ObjectMapper mapper;

    @Override
    protected Class<WorkoutSchemaSnapshot> getResultClass() {
        return WorkoutSchemaSnapshot.class;
    }
}