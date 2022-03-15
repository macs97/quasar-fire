package co.com.quasarfire.domain.service.impl;

import co.com.quasarfire.domain.enums.Satellites;
import co.com.quasarfire.domain.response.Coordinates;
import co.com.quasarfire.facade.interfaces.GetMessageFacade;
import co.com.quasarfire.facade.interfaces.GetPositionFacade;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
class CommandCenterServiceImplTest {

    @MockBean
    private GetPositionFacade getPositionFacade;

    @MockBean
    private GetMessageFacade getMessageFacade;

    @Autowired
    private CommandCenterServiceImpl commandCenterService;

    @Test
    void getLocationSuccessTest() {
        Gson gson = new Gson();
        List<Float> distances = List.of(100f, 0f, 0f);
        List<String> coordinatesStringExpected = List.of("-475", "1550");
        Coordinates coordinatesExpected = Coordinates.builder().x(-475).y(1550).build();

        Mockito.when(this.getPositionFacade.getPosition(Mockito.anyList())).thenReturn(coordinatesStringExpected);

        Coordinates coordinates = this.commandCenterService.getLocation(distances);

        Assertions.assertEquals(gson.toJson(coordinatesExpected), gson.toJson(coordinates));
        Assertions.assertEquals(coordinatesExpected.getX(), coordinates.getX());
        Assertions.assertEquals(coordinatesExpected.getY(), coordinates.getY());
    }

    @Test
    void getMessageSuccessTest() {
        String messageExpected = "este es un mensaje secreto";
        Map<Integer, List<String>> messagesBySatellite = new HashMap<>();
        messagesBySatellite.put(Satellites.KENOBI.getOrder(), List.of("este", "", "", "mensaje", ""));
        messagesBySatellite.put(Satellites.SKYWALKER.getOrder(), List.of("", "es", "", "", "secreto"));
        messagesBySatellite.put(Satellites.SATO.getOrder(), List.of("este", "", "un", "", ""));

        Mockito.when(this.getMessageFacade.getMessage(Mockito.anyMap())).thenReturn(messageExpected);

        String message = this.commandCenterService.getMessage(messagesBySatellite);

        Assertions.assertEquals(messageExpected, message);
    }
}
