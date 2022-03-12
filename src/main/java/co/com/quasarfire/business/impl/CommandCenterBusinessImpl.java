package co.com.quasarfire.business.impl;

import co.com.quasarfire.business.interfaces.CommandCenterBusiness;
import co.com.quasarfire.domain.enums.Satellites;
import co.com.quasarfire.domain.request.SatelliteDetail;
import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;
import co.com.quasarfire.domain.response.Coordinates;
import co.com.quasarfire.domain.service.interfaces.CommandCenterService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandCenterBusinessImpl implements CommandCenterBusiness {

    private final CommandCenterService commandCenterService;

    @Autowired
    public CommandCenterBusinessImpl(
        CommandCenterService commandCenterService) {
        this.commandCenterService = commandCenterService;
    }

    @Override
    public CommandCenterResponse getSpaceshipPositionAndMessage(SatellitesRequest satellitesRequest) {

        /*Coordinates coordinates = commandCenterService.getLocation(
            satellitesRequest.getSatellites().stream().map(SatelliteDetail::getDistance).collect(
                Collectors.toList()));*/

        String message = this.commandCenterService.getMessage(satellitesRequest.getSatellites().stream().collect(
            Collectors.toMap(key -> Satellites.getByName(key.getName()).getOrder(), SatelliteDetail::getMessage)));

        return CommandCenterResponse.builder()
            .message(message)
            .build();
    }
}
