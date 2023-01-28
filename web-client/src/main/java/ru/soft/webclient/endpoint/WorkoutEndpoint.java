package ru.soft.webclient.endpoint;

import dev.hilla.Endpoint;
import ru.soft.common.api.ApiController;
import ru.soft.common.to.WorkoutTo;

@Endpoint
public class WorkoutEndpoint extends BaseEndpoint<WorkoutTo> {

    public WorkoutEndpoint(ApiController<WorkoutTo> controller) {
        super(controller);
    }
}
