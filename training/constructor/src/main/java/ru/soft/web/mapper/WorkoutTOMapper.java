package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.common.to.RoundTo;
import ru.soft.data.model.Round;

@Component
public class WorkoutTOMapper implements TOMapper<Round, RoundTo> {

    public RoundTo toTo(Round entity) {
        return RoundTo.builder()
                .id(entity.id())
                .workoutSchema(entity.workoutSchema())
                .title(entity.title())
                .note(entity.note())
                .build();
    }

    public Round fromTo(RoundTo to) {
        return Round.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .workoutSchema(to.workoutSchema())
                .title(to.title())
                .note(to.note())
                .build();
    }
}
