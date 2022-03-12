package co.com.quasarfire.facade.impl;

import co.com.quasarfire.facade.interfaces.GetMessageFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class GetMessageFacadeImpl implements GetMessageFacade {

    @Override
    public String getMessage(Map<Integer, List<String>> messagesBySatellite) {
        List<String> finalMessage = initializeList(messagesBySatellite);
        AtomicInteger i = new AtomicInteger();
        this.cleanOutOfPhase(messagesBySatellite);
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
        return String.join(" ", finalMessage);
    }

    private void cleanOutOfPhase(Map<Integer, List<String>> messagesBySatellite) {
        if (!this.isOutOfPhase(messagesBySatellite)) {
            int minSize = this.minSize(messagesBySatellite);
            int sizeToRemove = this.maxSize(messagesBySatellite) - minSize;
            removeOutOfPhase(messagesBySatellite, minSize, sizeToRemove);
        }
    }

    private Integer maxSize(Map<Integer, List<String>> messagesBySatellite) {
        return this.getSizes(messagesBySatellite).max(Integer::compare).orElse(0);
    }

    private Integer minSize(Map<Integer, List<String>> messagesBySatellite) {
        return this.getSizes(messagesBySatellite).min(Integer::compare).orElse(0);
    }

    private boolean isOutOfPhase(Map<Integer, List<String>> messagesBySatellite) {
        return this.getSizes(messagesBySatellite).collect(Collectors.toSet()).size() == 1;
    }

    private Stream<Integer> getSizes(Map<Integer, List<String>> messagesBySatellite) {
        return messagesBySatellite.values().stream().map(List::size);
    }

    private void removeOutOfPhase(Map<Integer, List<String>> messagesBySatellite, int minSize, int sizeToRemove) {
        messagesBySatellite.values().stream().filter(value -> value.size() > minSize).forEach(value -> value.subList(0, sizeToRemove).clear());
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
        for (int i = 0; i < this.maxSize(messagesBySatellite); i++) {
            finalMessage.add("");
        }
        return finalMessage;
    }
}
