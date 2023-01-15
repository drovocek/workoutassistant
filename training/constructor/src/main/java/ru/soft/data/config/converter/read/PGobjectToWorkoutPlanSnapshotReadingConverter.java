package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ru.soft.common.data.snapshot.WorkoutSnapshot;

@RequiredArgsConstructor
public class PGobjectToWorkoutPlanSnapshotReadingConverter implements Converter<PGobject, WorkoutSnapshot> {

    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public WorkoutSnapshot convert(@NonNull PGobject source) {
        String workoutRoundSchemaJson = source.getValue();
        return this.mapper.readValue(workoutRoundSchemaJson, WorkoutSnapshot.class);
    }
}