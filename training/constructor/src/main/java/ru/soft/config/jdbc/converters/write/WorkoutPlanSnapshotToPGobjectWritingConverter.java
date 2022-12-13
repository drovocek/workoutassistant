package ru.soft.config.jdbc.converters.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.data.model.snapshot.WorkoutPlanSnapshot;

@Getter
@RequiredArgsConstructor
public class WorkoutPlanSnapshotToPGobjectWritingConverter extends ObjectToPGobjectWritingConverter<WorkoutPlanSnapshot> {

    private final ObjectMapper mapper;
}