package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutSession;
import ru.soft.common.to.WorkoutSessionTo;

@Component
public class WorkoutSessionTOMapper implements TOMapper<WorkoutSession, WorkoutSessionTo> {

    public WorkoutSessionTo toTo(WorkoutSession entity) {
        return new WorkoutSessionTo(
                entity.id(),
                entity.plan(),
                entity.dateTime(),
                entity.note()
        );
    }

    public WorkoutSession fromTo(WorkoutSessionTo to) {
        return WorkoutSession.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .plan(to.plan())
                .dateTime(to.dateTime())
                .note(to.note())
                .build();
    }
}
