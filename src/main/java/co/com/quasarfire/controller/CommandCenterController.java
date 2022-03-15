package co.com.quasarfire.controller;

import co.com.quasarfire.business.interfaces.CommandCenterBusiness;
import co.com.quasarfire.domain.request.SatelliteDetail;
import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandCenterController {

    private final CommandCenterBusiness commandCenter;

    @Autowired
    public CommandCenterController(CommandCenterBusiness commandCenter) {
        this.commandCenter = commandCenter;
    }

    @PostMapping("/topsecret")
    public ResponseEntity<CommandCenterResponse> getSpaceshipPositionAndMessage(
        @RequestBody SatellitesRequest satellitesRequest) {
        return new ResponseEntity<>(this.commandCenter.getSpaceshipPositionAndMessage(satellitesRequest),
            HttpStatus.OK);
    }

    @GetMapping("/topsecret_split/{name}")
    public ResponseEntity<CommandCenterResponse> getPositionAndMessageBySpaceship(@PathVariable String name,
        @RequestBody SatelliteDetail satelliteDetail) {

        CommandCenterResponse commandCenterResponse = this.commandCenter.getSpaceshipPositionAndMessage(
            SatellitesRequest.builder().satellites(List.of(
                SatelliteDetail.builder().name(name).distance(satelliteDetail.getDistance())
                    .message(satelliteDetail.getMessage()).build())).build());
        if (commandCenterResponse.getMessage().isEmpty()) {
            commandCenterResponse.setMessage("No hay suficiente informacion");
        }
        return new ResponseEntity<>(commandCenterResponse, HttpStatus.OK);

    }

    @PostMapping("/topsecret_split/{name}")
    public ResponseEntity<CommandCenterResponse> postPositionAndMessageBySpaceship(@PathVariable String name,
        @RequestBody SatelliteDetail satelliteDetail) {
        SatellitesRequest satellitesRequest = SatellitesRequest.builder().satellites(List.of(
            SatelliteDetail.builder().name(name).distance(satelliteDetail.getDistance())
                .message(satelliteDetail.getMessage()).build())).build();
        return new ResponseEntity<>(this.commandCenter.getSpaceshipPositionAndMessage(satellitesRequest),
            HttpStatus.OK);
    }
}
