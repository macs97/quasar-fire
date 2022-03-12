package co.com.quasarfire.controller;

import co.com.quasarfire.business.interfaces.CommandCenterBusiness;
import co.com.quasarfire.domain.request.SatellitesRequest;
import co.com.quasarfire.domain.response.CommandCenterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandCenterController {

    private final CommandCenterBusiness commandCenter;

    public CommandCenterController(CommandCenterBusiness commandCenter) {
        this.commandCenter = commandCenter;
    }

    @PostMapping("/topsecret")
    public ResponseEntity<CommandCenterResponse> getSpaceshipPositionAndMessage(
        @RequestBody SatellitesRequest satellitesRequest) {
        return new ResponseEntity<>(this.commandCenter.getSpaceshipPositionAndMessage(satellitesRequest), HttpStatus.OK);
    }
}
