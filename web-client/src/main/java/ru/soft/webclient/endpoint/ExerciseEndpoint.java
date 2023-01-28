package ru.soft.webclient.endpoint;

import dev.hilla.Endpoint;
import ru.soft.common.api.ApiController;
import ru.soft.common.to.ExerciseTo;

@Endpoint
public class ExerciseEndpoint extends BaseEndpoint<ExerciseTo> {

    public ExerciseEndpoint(ApiController<ExerciseTo> controller) {
        super(controller);
    }
}
