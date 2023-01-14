package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

@RequiredArgsConstructor
public class WorkoutPlanSnapshotToPGobjectWritingConverter implements Converter<WorkoutPlanSnapshot, PGobject> {

    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public PGobject convert(@NonNull WorkoutPlanSnapshot source) {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(this.mapper.writeValueAsString(source));
        return pGobject;
    }
}