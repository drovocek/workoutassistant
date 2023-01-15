package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.TrainingSession;

@Component
public class WorkoutSessionTOMapper implements TOMapper<TrainingSession, WorkoutSessionTo> {

    public WorkoutSessionTo toTo(TrainingSession entity) {
        return new WorkoutSessionTo(
                entity.id(),
                entity.plan(),
                entity.dateTime(),
                entity.note()
        );
    }

    public TrainingSession fromTo(WorkoutSessionTo to) {
        return TrainingSession.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .plan(to.workoutSnapshot())
                .dateTime(to.dateTime())
                .note(to.note())
                .build();
    }
}
