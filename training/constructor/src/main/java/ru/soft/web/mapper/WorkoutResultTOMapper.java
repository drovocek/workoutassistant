package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutResult;
import ru.soft.common.to.WorkoutResultTo;

@Component
public class WorkoutResultTOMapper implements TOMapper<WorkoutResult, WorkoutResultTo> {

    public WorkoutResultTo toTo(WorkoutResult entity) {
        return new WorkoutResultTo(
                entity.getId(),
                entity.workoutSessionId(),
                entity.workoutSchemaSnapshot(),
                entity.title(),
                entity.description()
        );
    }

    public WorkoutResult fromTo(WorkoutResultTo to) {
        return WorkoutResult.builder()
                .id(to.id())
                .workoutSessionId(to.workoutSessionId())
                .isNew(to.id() == null)
                .workoutSchemaSnapshot(to.workoutSchemaSnapshot())
                .title(to.title())
                .description(to.description())
                .build();
    }
}
