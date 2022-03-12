package co.com.quasarfire.business.impl;

import co.com.quasarfire.business.interfaces.CommandCenterBusiness;
import co.com.quasarfire.domain.enums.Satellites;
import co.com.quasarfire.domain.request.SatelliteDetail;
import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;
import co.com.quasarfire.domain.response.Coordinates;
import co.com.quasarfire.domain.service.interfaces.CommandCenterService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandCenterBusinessImpl implements CommandCenterBusiness {

    private final CommandCenterService commandCenterService;

    @Autowired
    public CommandCenterBusinessImpl(CommandCenterService commandCenterService) {
        this.commandCenterService = commandCenterService;
    }

    @Override
    public CommandCenterResponse getSpaceshipPositionAndMessage(SatellitesRequest satellitesRequest) {
        this.setDistances(satellitesRequest);

        String message = this.commandCenterService.getMessage(satellitesRequest.getSatellites().stream().collect(
            Collectors.toMap(key -> Satellites.getByName(key.getName()).getOrder(), SatelliteDetail::getMessage)));

        Coordinates coordinates = commandCenterService.getLocation(
            satellitesRequest.getSatellites().stream().map(SatelliteDetail::getDistance).collect(Collectors.toList()));

        return CommandCenterResponse.builder().position(coordinates).message(message).build();
    }

    private void setDistances(SatellitesRequest satellitesRequest) {
        if (satellitesRequest.getSatellites().size() < Satellites.values().length) {
            List<SatelliteDetail> satellitesDetails = new ArrayList<>(Arrays.asList(SatelliteDetail.builder().build(), SatelliteDetail.builder().build(), SatelliteDetail.builder().build()));
            Arrays.stream(Satellites.values()).forEach(satellite -> {
                if (satellite.getName().equals(satellitesRequest.getSatellites().get(0).getName().toUpperCase())) {
                    satellitesDetails.add(satellite.getOrder(), SatelliteDetail.builder().name(satellite.getName())
                        .distance(satellitesRequest.getSatellites().get(0).getDistance())
                        .message(satellitesRequest.getSatellites().get(0).getMessage()).build());
                } else {
                    satellitesDetails.add(satellite.getOrder(),
                        SatelliteDetail.builder().name(satellite.getName()).distance(0).message(satellitesRequest.getSatellites().get(0).getMessage()).build());
                }
            });
            satellitesRequest.setSatellites(satellitesDetails);
            satellitesRequest.getSatellites().removeIf(satelliteDetail -> satelliteDetail.getName() == null);
        }
    }
}
