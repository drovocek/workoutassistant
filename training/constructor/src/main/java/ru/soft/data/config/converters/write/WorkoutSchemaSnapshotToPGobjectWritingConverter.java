package ru.soft.data.config.converters.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

@Getter
@RequiredArgsConstructor
public class WorkoutSchemaSnapshotToPGobjectWritingConverter extends ObjectToPGobjectWritingConverter<WorkoutSchemaSnapshot> {

    private final ObjectMapper mapper;
}