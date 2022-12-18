package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

@Getter
@RequiredArgsConstructor
public class WorkoutSchemaSnapshotToPGobjectWritingConverter extends ObjectToPGobjectWritingConverter<WorkoutSchemaSnapshot> {

    private final ObjectMapper mapper;
}