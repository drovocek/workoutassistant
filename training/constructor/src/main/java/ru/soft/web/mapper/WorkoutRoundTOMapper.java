package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.Round;
import ru.soft.common.to.RoundTo;

@Component
public class WorkoutRoundTOMapper implements TOMapper<Round, RoundTo> {

    public RoundTo toTo(Round entity) {
        return new RoundTo(
                entity.getId(),
                entity.stationsSchema(),
                entity.title(),
                entity.description()
        );
    }

    public Round fromTo(RoundTo to) {
        return Round.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .stationsSchema(to.stationsSchema())
                .title(to.title())
                .description(to.description())
                .build();
    }
}
