package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.web.to.WorkoutPlanTo;

@Component
public class WorkoutPlanTOMapper implements TOMapper<WorkoutPlan, WorkoutPlanTo> {

    public WorkoutPlanTo toTo(WorkoutPlan entity) {
        return new WorkoutPlanTo(
                entity.getId(),
                entity.workoutSchemaSnapshot(),
                entity.title(),
                entity.description()
        );
    }

    public WorkoutPlan fromTo(WorkoutPlanTo to) {
        return WorkoutPlan.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .workoutSchemaSnapshot(to.workoutSchemaSnapshot())
                .title(to.title())
                .description(to.description())
                .build();
    }
}
