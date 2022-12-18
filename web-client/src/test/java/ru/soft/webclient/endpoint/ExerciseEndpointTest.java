package ru.soft.webclient.endpoint;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.soft.common.to.ExerciseTo;

import java.util.UUID;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


//https://medium.com/@gavinklfong/how-to-automate-tests-for-spring-boot-api-using-mockserver-c6221ea8c549
//https://github.com/mock-server/mockserver/tree/master/mockserver-examples
//@MockServerTest("server.url=http://localhost:${mockServerPort}")
@SpringBootTest
public class ExerciseEndpointTest {

    private MockServerClient mockServer = new MockServerClient("localhost",9000);
    //
//    @Autowired
//    private ClientAndServer mockServer;
    @Autowired
    private ExerciseEndpoint endpoint;

    @Test
    void test(){
        this.mockServer
                .when(request().withPath("/callback"))
                .respond(response().withBody("ssss"));
        ExerciseTo exerciseTo = endpoint.get(UUID.randomUUID());
    }
}
