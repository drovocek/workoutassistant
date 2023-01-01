package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutRound;
import ru.soft.common.to.WorkoutRoundTo;

@Component
public class WorkoutRoundTOMapper implements TOMapper<WorkoutRound, WorkoutRoundTo> {

    public WorkoutRoundTo toTo(WorkoutRound entity) {
        return new WorkoutRoundTo(
                entity.getId(),
                entity.workoutRoundSchemaSnapshot(),
                entity.title(),
                entity.description()
        );
    }

    public WorkoutRound fromTo(WorkoutRoundTo to) {
        return WorkoutRound.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .workoutRoundSchemaSnapshot(to.roundSchema())
                .title(to.title())
                .description(to.description())
                .build();
    }
}
