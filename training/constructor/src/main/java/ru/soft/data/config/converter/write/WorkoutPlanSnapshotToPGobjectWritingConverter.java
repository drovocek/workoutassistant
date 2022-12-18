package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

@Getter
@RequiredArgsConstructor
public class WorkoutPlanSnapshotToPGobjectWritingConverter extends ObjectToPGobjectWritingConverter<WorkoutPlanSnapshot> {

    private final ObjectMapper mapper;
}