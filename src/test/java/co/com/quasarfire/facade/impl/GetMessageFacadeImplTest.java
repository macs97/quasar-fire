package co.com.quasarfire.facade.impl;

import co.com.quasarfire.domain.enums.Satellites;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class GetMessageFacadeImplTest {

    @Autowired
    private GetMessageFacadeImpl getMessageFacade;

    @Test
    void getMessageSuccessTest() {
        String messageExpected = "este es un mensaje secreto";
        Map<Integer, List<String>> messagesBySatellite = new HashMap<>();
        messagesBySatellite.put(Satellites.KENOBI.getOrder(), List.of("este", "", "", "mensaje", ""));
        messagesBySatellite.put(Satellites.SKYWALKER.getOrder(), List.of("", "es", "", "", "secreto"));
        messagesBySatellite.put(Satellites.SATO.getOrder(), List.of("este", "", "un", "", ""));

        String message = this.getMessageFacade.getMessage(messagesBySatellite);

        Assertions.assertEquals(messageExpected, message);
    }

    @Test
    void getMessageSuccessOutOfPhaseTest() {
        String messageExpected = "este es un mensaje";
        Map<Integer, List<String>> messagesBySatellite = new HashMap<>();

        List<String> messagesKenobi = new ArrayList<>();
        messagesKenobi.add("");
        messagesKenobi.add("este");
        messagesKenobi.add("es");
        messagesKenobi.add("un");
        messagesKenobi.add("mensaje");

        List<String> messagesSkywalker = new ArrayList<>();
        messagesSkywalker.add("este");
        messagesSkywalker.add("");
        messagesSkywalker.add("un");
        messagesSkywalker.add("mensaje");

        List<String> messagesSato = new ArrayList<>();
        messagesSato.add("");
        messagesSato.add("");
        messagesSato.add("es");
        messagesSato.add("");
        messagesSato.add("mensaje");

        messagesBySatellite.put(Satellites.KENOBI.getOrder(), messagesKenobi);
        messagesBySatellite.put(Satellites.SKYWALKER.getOrder(), messagesSkywalker);
        messagesBySatellite.put(Satellites.SATO.getOrder(), messagesSato);

        String message = this.getMessageFacade.getMessage(messagesBySatellite);

        Assertions.assertEquals(messageExpected, message);
    }

}
