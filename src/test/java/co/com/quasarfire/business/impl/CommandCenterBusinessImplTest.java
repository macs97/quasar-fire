package co.com.quasarfire.business.impl;


import co.com.quasarfire.domain.request.SatelliteDetail;
import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;
import co.com.quasarfire.domain.response.Coordinates;
import co.com.quasarfire.domain.service.interfaces.CommandCenterService;
import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommandCenterBusinessImplTest {

    @MockBean
    private CommandCenterService commandCenterService;

    @Autowired
    private CommandCenterBusinessImpl commandCenterBusiness;

    @Test
    void getSpaceshipPositionAndMessageWithoutSetDistancesTest() {
        Gson gson = new Gson();
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().name("kenobi").distance(100).message(List.of("este", "", "", "mensaje", ""))
                    .build(),
                SatelliteDetail.builder().name("skywalker").distance(115.5f).message(List.of("", "es", "", "", "secreto"))
                    .build(),
                SatelliteDetail.builder().name("sato").distance(142.7f).message(List.of("este", "", "un", "", "")).build()))
            .build();

        String messageExpected = "este es un mensaje secreto";
        Coordinates coordinatesExpected = Coordinates.builder().x(-487.29f).y(1557.01f).build();

        CommandCenterResponse commandCenterResponseExpected = CommandCenterResponse.builder()
            .position(coordinatesExpected).message(messageExpected).build();

        Mockito.when(this.commandCenterService.getMessage(Mockito.anyMap())).thenReturn(messageExpected);
        Mockito.when(this.commandCenterService.getLocation(Mockito.anyList())).thenReturn(coordinatesExpected);

        CommandCenterResponse commandCenterResponse = this.commandCenterBusiness.getSpaceshipPositionAndMessage(
            satellitesRequest);

        Assertions.assertEquals(gson.toJson(commandCenterResponseExpected), gson.toJson(commandCenterResponse));
        Assertions.assertEquals(messageExpected, commandCenterResponse.getMessage());
    }

    @Test
    void getSpaceshipPositionAndMessageWithSetDistancesTest() {
        Gson gson = new Gson();
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().name("kenobi").distance(100).message(List.of("este", "", "", "mensaje", ""))
                    .build()))
            .build();

        String messageExpected = "este mensaje";
        Coordinates coordinatesExpected = Coordinates.builder().x(-487.29f).y(1557.01f).build();

        CommandCenterResponse commandCenterResponseExpected = CommandCenterResponse.builder()
            .position(coordinatesExpected).message(messageExpected).build();

        Mockito.when(this.commandCenterService.getMessage(Mockito.anyMap())).thenReturn(messageExpected);
        Mockito.when(this.commandCenterService.getLocation(Mockito.anyList())).thenReturn(coordinatesExpected);

        CommandCenterResponse commandCenterResponse = this.commandCenterBusiness.getSpaceshipPositionAndMessage(
            satellitesRequest);


        Assertions.assertEquals(gson.toJson(commandCenterResponseExpected), gson.toJson(commandCenterResponse));
        Assertions.assertEquals(messageExpected, commandCenterResponse.getMessage());
    }

}
