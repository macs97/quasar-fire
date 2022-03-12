package co.com.quasarfire.domain.service.impl;

import co.com.quasarfire.domain.response.Coordinates;
import co.com.quasarfire.domain.service.interfaces.CommandCenterService;
import co.com.quasarfire.facade.interfaces.GetPositionFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CommandCenterServiceImpl implements CommandCenterService {

    private final GetPositionFacade getPositionFacade;

    public CommandCenterServiceImpl(GetPositionFacade getPositionFacade) {
        this.getPositionFacade = getPositionFacade;
    }

    @Override
    public Coordinates getLocation(List<Float> distances) {
        List<String> coordinates = this.getPositionFacade.getPosition(distances);
        return Coordinates.builder().x(Float.valueOf(coordinates.get(0))).y(Float.valueOf(coordinates.get(1))).build();
    }

    @Override
    public String getMessage(Map<Integer, List<String>> messagesBySatellite) {
        List<String> finalMessage = initializeList(messagesBySatellite);
        AtomicInteger i = new AtomicInteger();
        //Integer initialPosition = this.getInitialPosition(messagesBySatellite);
        messagesBySatellite.get(1).forEach(messageBySatellite -> {
            if (messageBySatellite.isEmpty()) {
                Optional<String> messagePosition = getMessageRespectivePosition(messagesBySatellite, i.get());
                if (messagePosition.isPresent() && !finalMessage.get(i.get()).equals(messagePosition.get())) {
                    this.addMessageToList(finalMessage, i.get(), messagePosition.get());
                }
                i.addAndGet(1);
            } else {
                this.addMessageToList(finalMessage, i.get(), messageBySatellite);
                i.addAndGet(1);
            }
        });

        finalMessage.removeIf(String::isEmpty);
        return finalMessage.stream().collect(Collectors.joining(" "));
    }

    private Integer getInitialPosition(Map<Integer, List<String>> messagesBySatellite) {
        //TODO: arreglar el desfaje
       Map<Integer, Integer> satellitesWithMessagesSize = messagesBySatellite.entrySet().stream()
           .collect(Collectors.toMap(key -> key.getKey(), key -> key.getValue().size()));
       boolean isOutOfPhase = satellitesWithMessagesSize.values().stream().collect(Collectors.toSet()).size()==1;

       if(!isOutOfPhase) {
           //satellitesWithMessagesSize.entrySet().stream().map(Entry::getValue).max(Integer::compare).ifPresent();
       }
        return 1;
    }

    private Optional<String> getMessageRespectivePosition(Map<Integer, List<String>> messagesBySatellite, int i) {
        return messagesBySatellite.values().stream().map(messages -> messages.get(i))
            .filter(message -> !message.isEmpty()).distinct().findFirst();
    }

    private void addMessageToList(List<String> finalMessage, int position, String message) {
        finalMessage.add(position, message);
    }

    private List<String> initializeList(Map<Integer, List<String>> messagesBySatellite) {
        List<String> finalMessage = new ArrayList<>();
        int maxSize = messagesBySatellite.values().stream().map(List::size).sorted().findFirst().get();
        for (int i = 0; i < maxSize; i++) {
            finalMessage.add("");
        }
        return finalMessage;
    }

}
