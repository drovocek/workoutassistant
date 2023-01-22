package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutTo;
import ru.soft.data.model.Workout;

@Component
public class TrainingSessionTOMapper implements TOMapper<Workout, WorkoutTo> {

    public WorkoutTo toTo(Workout entity) {
        return WorkoutTo.builder()
                .id(entity.id())
                .workoutSchema(entity.workoutSchema())
                .dateTime(entity.dateTime())
                .title(entity.title())
                .note(entity.note())
                .build();
    }

    public Workout fromTo(WorkoutTo to) {
        return Workout.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .workoutSchema(to.workoutSchema())
                .dateTime(to.dateTime())
                .title(to.title())
                .note(to.note())
                .build();
    }
}
