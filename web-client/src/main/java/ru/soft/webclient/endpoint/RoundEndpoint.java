package ru.soft.webclient.endpoint;

import dev.hilla.Endpoint;
import ru.soft.common.api.ApiController;
import ru.soft.common.to.RoundTo;

@Endpoint
public class RoundEndpoint extends BaseEndpoint<RoundTo> {

    public RoundEndpoint(ApiController<RoundTo> controller) {
        super(controller);
    }
}
