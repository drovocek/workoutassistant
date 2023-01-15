package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.Program;

@Component
public class WorkoutSessionTOMapper implements TOMapper<Program, WorkoutSessionTo> {

    public WorkoutSessionTo toTo(Program entity) {
        return new WorkoutSessionTo(
                entity.id(),
                entity.plan(),
                entity.dateTime(),
                entity.note()
        );
    }

    public Program fromTo(WorkoutSessionTo to) {
        return Program.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .plan(to.workoutSnapshot())
                .dateTime(to.dateTime())
                .note(to.note())
                .build();
    }
}
