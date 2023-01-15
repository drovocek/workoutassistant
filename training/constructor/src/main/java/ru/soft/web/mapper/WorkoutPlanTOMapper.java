package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.Workout;
import ru.soft.common.to.WorkoutTo;

@Component
public class WorkoutPlanTOMapper implements TOMapper<Workout, WorkoutTo> {

    public WorkoutTo toTo(Workout entity) {
        return new WorkoutTo(
                entity.getId(),
                entity.roundsSchema(),
                entity.title(),
                entity.description()
        );
    }

    public Workout fromTo(WorkoutTo to) {
        return Workout.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .roundsSchema(to.roundsSchema())
                .title(to.title())
                .description(to.description())
                .build();
    }
}
