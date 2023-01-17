package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.common.to.TrainingSessionTo;
import ru.soft.data.model.TrainingSession;

@Component
public class TrainingSessionTOMapper implements TOMapper<TrainingSession, TrainingSessionTo> {

    public TrainingSessionTo toTo(TrainingSession entity) {
        return TrainingSessionTo.builder()
                .id(entity.id())
                .workoutSchema(entity.workoutSchema())
                .dateTime(entity.dateTime())
                .title(entity.title())
                .note(entity.note())
                .build();
    }

    public TrainingSession fromTo(TrainingSessionTo to) {
        return TrainingSession.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .workoutSchema(to.workoutSchema())
                .dateTime(to.dateTime())
                .title(to.title())
                .note(to.note())
                .build();
    }
}
