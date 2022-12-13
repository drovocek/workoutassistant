package ru.soft.data.config.converters.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

@Getter
@RequiredArgsConstructor
public class WorkoutRoundSchemaSnapshotToPGobjectWritingConverter extends ObjectToPGobjectWritingConverter<WorkoutRoundSchemaSnapshot> {

    private final ObjectMapper mapper;
}